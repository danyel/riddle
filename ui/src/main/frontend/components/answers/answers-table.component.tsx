import {useEffect, useState} from "react";
import {Dialog, FormLayout, FormRow, Grid, GridColumn, HorizontalLayout, TextArea} from "@vaadin/react-components";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import {AnswerEndpoint} from "Frontend/generated/endpoints";
import Answer from "Frontend/generated/be/riddler/v1/answer/domain/Answer";
import {useSignal} from "@vaadin/hilla-react-signals";
import CreateAnswer from "Frontend/generated/be/riddler/v1/answer/domain/CreateAnswer";
import {CancelButton, CheckButton, CloseButton, PlusButton, ViewDetailButton} from "Frontend/components/ui/button";
import UpdateAnswer from "Frontend/generated/be/riddler/v1/answer/domain/UpdateAnswer";

export interface AnswersTableProperties {
    questionId: string;
}

export default function AnswersTable(props: AnswersTableProperties) {
    const [answers, setAnswers] = useState<Answer[]>([]);
    const [open, setOpen] = useState(false);
    const [answer, setAnswer] = useState<Answer>();
    const [editOpen, setEditOpen] = useState(false);

    const fetchAnswers = () => {
        AnswerEndpoint.findByQuestion(props.questionId)
            .then(setAnswers);
    };

    useEffect(() => {
        fetchAnswers();
    }, [props.questionId]);

    const answerActions = () => {
        return <ViewDetailButton onClick={() => {
            setAnswer(answer);
            setOpen(false);
            setEditOpen(true);
        }}/>;
    };

    return (
        <>
            <HorizontalLayout className={styles.full_width_layout}>
                <div className={styles.menu_bar_layout}>
                    <PlusButton onClick={() => setOpen(true)}/>
                </div>
            </HorizontalLayout>
            {/* 1. We pass down a close handler to reset parent state */}
            <CreateAnswerDialogModal
                show={open}
                questionId={props.questionId}
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
        </>
    );
}

function CreateAnswerDialogModal(props: {
    show: boolean;
    questionId: string;
    onAnswerCreated: () => void;
    onClose: () => void;
}) {
    const answerValue = useSignal('');

    useEffect(() => {
        if (props.show) {
            // Clear input value when the modal opens
            answerValue.value = '';
        }
    }, [props.show]);

    function saveAnswer() {
        const payload: CreateAnswer = {question_id: props.questionId, value: answerValue.value};
        AnswerEndpoint.create(payload)
            .then(() => {
                props.onAnswerCreated();
                props.onClose();
            });
    }

    function closeIfNotValue(e: CustomEvent<{ value: boolean }>) {
        // Syncs background clicks / ESC keys directly back to parent
        if (!e.detail.value) props.onClose();
    }

    return (
        <Dialog
            width={"100vh"}
            height={"100vh"}
            header-title="Create answer"
            opened={props.show}
            onOpenedChanged={closeIfNotValue}
            header={<CancelButton onClick={() => props.onClose()}/>}
            footerRenderer={() => (
                <>
                    <CloseButton onClick={props.onClose}/>
                    <CheckButton onClick={saveAnswer}/>
                </>
            )}
        >
            <FormLayout
                style={{width: '100%'}}
                autoResponsive
                columnWidth="8em"
                expandColumns
                expandFields
            >
                <FormRow>
                    <TextArea
                        label="Answer"
                        value={answerValue.value}
                        onValueChanged={(e) => (answerValue.value = e.detail.value)}
                        className={styles.text_area_full}
                    />
                </FormRow>
            </FormLayout>
        </Dialog>
    );
}

function EditAnswerDialogModal(props: {
    show: boolean;
    onAnswerCreated: () => void;
    onClose: () => void;
    answer: string;
    answerId: string;
}) {
    const answerValue = useSignal<string>('');

    useEffect(() => {
        console.log(props.answer);
        answerValue.value = props.answer;
    }, [props.answer]);

    function updateAnswer() {
        const payload: UpdateAnswer = {value: answerValue.value};
        AnswerEndpoint.update(props.answerId, payload)
            .then(() => {
                props.onAnswerCreated();
                props.onClose();
            });
    }

    function closeIfNotValue(e: CustomEvent<{ value: boolean }>) {
        // Syncs background clicks / ESC keys directly back to parent
        if (!e.detail.value) props.onClose();
    }

    return (
        <Dialog
            width={"100vh"}
            height={"100vh"}
            header-title="Update answer"
            opened={props.show}
            onOpenedChanged={closeIfNotValue}
            header={<CancelButton onClick={() => props.onClose()}/>}
            footerRenderer={() => (
                <>
                    <CloseButton onClick={props.onClose}/>
                    <CheckButton onClick={updateAnswer}/>
                </>
            )}
        >
            <FormLayout
                style={{width: '100%'}}
                autoResponsive
                columnWidth="8em"
                expandColumns
                expandFields
            >
                <FormRow>
                    <TextArea
                        label="Answer"
                        value={answerValue.value}
                        onValueChanged={(e) => (answerValue.value = e.detail.value)}
                        className={styles.text_area_full}
                    />
                </FormRow>
            </FormLayout>
        </Dialog>
    );
}
