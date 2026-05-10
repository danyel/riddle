import {useNavigate, useParams} from "react-router";
import {useEffect, useState} from "react";
import {QuestionEndpoint, QuestionTypeEndpoint} from "Frontend/generated/endpoints";
import Question from "Frontend/generated/be/riddler/v1/question/client/model/Question";
import AnswersTable from "Frontend/components/answers/answers-table.component";
import {Dialog, HorizontalLayout, Select, TextArea, VerticalLayout} from "@vaadin/react-components";
// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';
import QuestionType from "Frontend/generated/be/riddler/v1/question/client/model/QuestionType";
import UpdateQuestion from "Frontend/generated/be/riddler/v1/question/client/model/UpdateQuestion";
import {BanButton, CheckButton, CloseButton} from "Frontend/components/ui/button";
import {useSignal} from "@vaadin/hilla-react-signals";
import {Button} from "@vaadin/react-components/Button.js";
import {ElementStylingTypes} from "Frontend/constant";


export default function QuestionDetailView() {
    const [question, setQuestion] = useState<Question>();
    const [items, setItems] = useState<{ label: string, value: string }[]>([]);
    const {id} = useParams();
    const navigate = useNavigate();
    const dialogOpened = useSignal(false);

    function open() {
        dialogOpened.value = true;
    }

    function close() {
        dialogOpened.value = false;
    }

    function deleteQuestion() {
        QuestionEndpoint.delete(id!!)
            .then(() => navigate('/questions'));
    }

    useEffect(() => {
        QuestionEndpoint.get(id).then(setQuestion);
        QuestionTypeEndpoint.questionTypes()
            .then((questionTypes: string[]) => {
                setItems(questionTypes.map(e => {
                    return {label: `label.${e}`, value: e};
                }));
            });
    }, [id]);

    return question && (<>
            <HorizontalLayout className={styles.full_width_layout}>
                <div className={styles.menu_bar_layout}>
                    <CheckButton onClick={() => {
                        const updateQuestion: UpdateQuestion = {question: question?.question!!, type: question?.type!!};
                        QuestionEndpoint.update(question?.id!!, updateQuestion)
                            .then(() => navigate('/questions'));
                    }}/>
                    <BanButton onClick={() => {
                        open();
                    }}/>
                    <CloseButton onClick={() => {
                        navigate('/questions');
                    }}/>
                </div>
            </HorizontalLayout>
            <Dialog
                headerTitle={`Delete user "${question.question}"?`}
                opened={dialogOpened.value}
                onClosed={() => {
                    dialogOpened.value = false;
                }}
                footer={
                    <>
                        <Button theme={ElementStylingTypes.PRIMARY_ERROR} onClick={deleteQuestion}
                                style={{marginRight: 'auto'}}>
                            Delete
                        </Button>
                        <Button theme="tertiary" onClick={close}>
                            Cancel
                        </Button>
                    </>
                }
            >
                Are you sure you want to delete this user permanently?
            </Dialog>
            <HorizontalLayout className={styles.full_width_layout}>
                <VerticalLayout style={{width: '100%'}}>
                    <HorizontalLayout className={styles.full_width_layout}>
                        <TextArea value={question.question}
                                  className={styles.text_area_full}
                                  onChange={(e) => {
                                      setQuestion(prev => prev ? {...prev, question: e.target.value} : undefined);
                                  }}
                        />
                    </HorizontalLayout>
                    <HorizontalLayout className={styles.full_width_layout}>
                        <Select
                            label="Question Type"
                            items={items}
                            value={question.type}
                            onValueChanged={(e) => {
                                const newType = e.detail.value as QuestionType; // String to QuestionType conversion

                                // Mutate state correctly using the updater function
                                setQuestion(prev => prev ? {...prev, type: newType} : undefined);
                            }}
                        />
                    </HorizontalLayout>
                    <AnswersTable questionId={question.id!!}/>
                </VerticalLayout>
            </HorizontalLayout>
        </>
    );
}
