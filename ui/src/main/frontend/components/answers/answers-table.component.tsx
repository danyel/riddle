import {useEffect, useState} from "react";
import {FormLayout, HorizontalLayout, TextArea, VerticalLayout} from "@vaadin/react-components";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import {AnswerEndpoint} from "Frontend/generated/endpoints";
import Answer from "Frontend/generated/be/riddler/v1/answer/client/model/Answer";
import {useSignal} from "@vaadin/hilla-react-signals";
import CreateAnswer from "Frontend/generated/be/riddler/v1/answer/client/model/CreateAnswer";
import {CloseButton, PlusButton, SaveButton, ViewDetailButton} from "Frontend/components/ui/button";
import UpdateAnswer from "Frontend/generated/be/riddler/v1/answer/client/model/UpdateAnswer";
import FormItem from "Frontend/components/ui/form/form-item.component";
import RiddlerModal from "Frontend/components/ui/modal/modal";
import RiddlerTable from "Frontend/components/table/table";
import {Notify} from "Frontend/util";

export default function AnswersTable(
    {
        questionId
    }:
    {
        questionId: string;
    }
) {
    const [answers, setAnswers] = useState<Answer[]>([]);
    const [open, setOpen] = useState(false);
    const [answer, setAnswer] = useState<Answer>();
    const [editOpen, setEditOpen] = useState(false);

    useEffect(() => {
        fetchAnswers();
    }, [questionId]);

    const fetchAnswers = () => {
        AnswerEndpoint.findByQuestion(questionId)
            .then(setAnswers)
            .catch(err => Notify.error('Could not fetch the answers {}', err));
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
                <RiddlerTable elements={answers}
                              columnNames={
                                  [
                                      {
                                          path: 'value',
                                          header: 'Answer',
                                          width: '240px',
                                          flexGrow: 1,
                                      }
                                  ]
                              }
                              emptyMessage={'No answers found'}
                              helperMessage={'Click \"+\" to add a new answer.'}
                              actionButtons={answerActions}/>
            </HorizontalLayout>
        </VerticalLayout>
    );
}

function CreateAnswerDialogModal(
    {
        show,
        questionId,
        onAnswerCreated,
        onClose
    }:
    {
        show: boolean;
        questionId: string;
        onAnswerCreated: () => void;
        onClose: () => void;
    }
) {
    const answerValue = useSignal('');

    useEffect(() => {
        if (show) {
            answerValue.value = '';
        }
    }, [show]);

    return (
        <RiddlerModal
            headerTitle={"Create answer"}
            opened={show}
            onClosed={onClose}
            footer={
                <>
                    <SaveButton onClick={() => {
                        const payload: CreateAnswer = {question_id: questionId, value: answerValue.value};
                        AnswerEndpoint.create(payload)
                            .then(() => {
                                onAnswerCreated();
                                onClose();
                            })
                            .catch(err => Notify.error('Could not create an answer {}', err));
                    }}/>
                    <CloseButton onClick={onClose}/>
                </>
            }
            content={
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
    );
}

function EditAnswerDialogModal(
    {
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
    }
) {
    const answerValue = useSignal<string>('');

    useEffect(() => {
        console.log(answer);
        answerValue.value = answer;
    }, [answer]);

    return (
        <RiddlerModal
            headerTitle={"Update answer"}
            opened={show}
            footer={
                <>
                    <SaveButton onClick={() => {
                        const payload: UpdateAnswer = {value: answerValue.value};
                        AnswerEndpoint.update(answerId, payload)
                            .then(() => {
                                onAnswerCreated();
                                onClose();
                            })
                            .catch(err => Notify.error('Could not update {}', err));
                    }}/>
                    <CloseButton onClick={onClose}/>
                </>
            }
            onClosed={onClose}
            content={
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
