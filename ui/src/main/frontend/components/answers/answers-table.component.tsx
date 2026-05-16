import {ReactNode, useEffect, useState} from "react";
import {
    Dialog,
    FormLayout,
    Grid,
    GridColumn,
    HorizontalLayout,
    TextArea,
    VerticalLayout
} from "@vaadin/react-components";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import {AnswerEndpoint} from "Frontend/generated/endpoints";
import Answer from "Frontend/generated/be/riddler/v1/answer/client/model/Answer";
import {useSignal} from "@vaadin/hilla-react-signals";
import CreateAnswer from "Frontend/generated/be/riddler/v1/answer/client/model/CreateAnswer";
import {CancelButton, CheckButton, CloseButton, PlusButton, ViewDetailButton} from "Frontend/components/ui/button";
import UpdateAnswer from "Frontend/generated/be/riddler/v1/answer/client/model/UpdateAnswer";
import FormItem from "Frontend/components/ui/form/form-item.component";

export interface AnswersTableProperties {
    questionId: string;
}

export default function AnswersTable({questionId}: AnswersTableProperties) {
    const [answers, setAnswers] = useState<Answer[]>([]);
    const [open, setOpen] = useState(false);
    const [answer, setAnswer] = useState<Answer>();
    const [editOpen, setEditOpen] = useState(false);

    useEffect(() => {
        fetchAnswers();
    }, [questionId]);

    const fetchAnswers = () => {
        AnswerEndpoint.findByQuestion(questionId)
            .then(setAnswers);
    };

    const answerActions = ({item}: { item: Answer }) => {
        return <ViewDetailButton onClick={() => {
            setAnswer(item);
            setOpen(false);
            setEditOpen(true);
        }}/>;
    };

    return (
        <VerticalLayout className={styles.full_width_layout}>
            <HorizontalLayout className={styles.full_width_layout}>
                <div className={styles.menu_bar_layout}>
                    <PlusButton onClick={() => setOpen(true)}/>
                </div>
            </HorizontalLayout>
            {/* 1. We pass down a close handler to reset parent state */}
            <CreateAnswerDialogModal
                show={open}
                questionId={questionId}
                onAnswerCreated={fetchAnswers}
                onClose={() => setOpen(false)}
            />
            <EditAnswerDialogModal
                show={editOpen}
                onAnswerCreated={fetchAnswers}
                onClose={() => setEditOpen(false)}
                answer={answer?.value!!}
                answerId={answer?.id!!}
            />
            <HorizontalLayout className={styles.full_width_layout}>
                <Grid items={answers} className={styles.riddler_table} allRowsVisible={true}>
                    <GridColumn path="value" header="Answer Value"/>
                    <GridColumn header={'Action'} renderer={answerActions}/>
                </Grid>
            </HorizontalLayout>
        </VerticalLayout>
    );
}

function AnswerDialogModal({label, show, onCheckButtonClicked, onClose, children}: {
    label: string,
    show: boolean,
    onCheckButtonClicked: () => void,
    onClose: () => void,
    children: ReactNode
}) {

    return (
        <Dialog
            width={"100vh"}
            height={"100vh"}
            header-title={label}
            onOpenedChanged={
                (e: CustomEvent<{ value: boolean }>): void => {
                    // Syncs background clicks / ESC keys directly back to parent
                    if (!e.detail.value) {
                        onClose();
                    }
                }
            }
            opened={show}
            header={<CancelButton onClick={onClose}/>}
            footerRenderer={() => (
                <>
                    <CheckButton onClick={onCheckButtonClicked}/>
                    <CloseButton onClick={onClose}/>
                </>
            )}
        >
            {children}
        </Dialog>
    );
}

function CreateAnswerDialogModal({show, questionId, onAnswerCreated, onClose}: {
    show: boolean;
    questionId: string;
    onAnswerCreated: () => void;
    onClose: () => void;
}) {
    const answerValue = useSignal('');

    useEffect(() => {
        if (show) {
            answerValue.value = '';
        }
    }, [show]);

    return (
        <AnswerDialogModal
            label={"Create answer"}
            show={show}
            onCheckButtonClicked={
                () => {
                    const payload: CreateAnswer = {question_id: questionId, value: answerValue.value};
                    AnswerEndpoint.create(payload)
                        .then(() => {
                            onAnswerCreated();
                            onClose();
                        });
                }
            }
            onClose={onClose}
            children={
                <FormLayout
                    style={{width: '100%'}}
                    autoResponsive
                    columnWidth="8em"
                    expandColumns
                    expandFields
                >
                    <FormItem children={
                        <TextArea
                            label="Answer"
                            value={answerValue.value}
                            onValueChanged={(e) => (answerValue.value = e.detail.value)}
                            className={styles.text_area_full}
                        />
                    }/>
                </FormLayout>
            }
        />
    )
        ;
}

function EditAnswerDialogModal({
                                   show,
                                   onAnswerCreated,
                                   onClose,
                                   answer,
                                   answerId
                               }: {
    show: boolean;
    onAnswerCreated: () => void;
    onClose: () => void;
    answer: string;
    answerId: string;
}) {
    const answerValue = useSignal<string>('');

    useEffect(() => {
        console.log(answer);
        answerValue.value = answer;
    }, [answer]);

    return (
        <AnswerDialogModal
            label={"Update answer"}
            show={show}
            onCheckButtonClicked={
                () => {
                    const payload: UpdateAnswer = {value: answerValue.value};
                    AnswerEndpoint.update(answerId, payload)
                        .then(() => {
                            onAnswerCreated();
                            onClose();
                        });
                }
            }
            onClose={onClose}
            children={
                <FormLayout
                    style={{width: '100%'}}
                    autoResponsive
                    columnWidth="8em"
                    expandColumns
                    expandFields
                >
                    <FormItem children={
                        <TextArea
                            label="Answer"
                            value={answerValue.value}
                            onValueChanged={(e) => (answerValue.value = e.detail.value)}
                            className={styles.text_area_full}
                        />
                    }/>
                </FormLayout>
            }/>
    );
}
