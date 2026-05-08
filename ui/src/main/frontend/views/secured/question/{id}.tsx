import {useNavigate, useParams} from "react-router";
import {useEffect, useState} from "react";
import {QuestionEndpoint, QuestionTypeEndpoint, TranslateEndpoint} from "Frontend/generated/endpoints";
import Question from "Frontend/generated/be/riddler/v1/question/api/Question";
import AnswersTable from "Frontend/components/answers/answers-table.component";
import {Button, HorizontalLayout, Icon, Select, TextArea, VerticalLayout} from "@vaadin/react-components";
// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';
import QuestionType from "Frontend/generated/be/riddler/v1/question/api/QuestionType";
import UpdateQuestion from "Frontend/generated/be/riddler/v1/question/api/UpdateQuestion";
import {IconsConstant} from "Frontend/constant/constants";


export default function QuestionDetailView() {
    const [question, setQuestion] = useState<Question>();
    const [translatedType, setTranslatedType] = useState<string>('');
    const [items, setItems] = useState<{ label: string, value: string }[]>([]);
    const {id} = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        console.log(`Trying to fetch ${id}`);
        QuestionEndpoint.get(id).then(setQuestion);
        QuestionTypeEndpoint.questionTypes()
            .then((questionTypes: string[]) => {
                setItems(questionTypes.map(e => {
                    return {label: `label.${e}`, value: e};
                }));
            });
    }, [id]);

    useEffect(() => {
        if (question?.type) {
            TranslateEndpoint.translate('en', `label.${question.type}`)
                .then(e => {
                    if (e && e.value) {
                        setTranslatedType(e.value);
                    }
                });
        }
    }, [question?.type]);

    return question && (<>
            <HorizontalLayout className={styles.question_menu_bar}>
                <Button theme="primary" onClick={() => {
                    const updateQuestion: UpdateQuestion = {question: question?.question!!, type: question?.type!!};
                    QuestionEndpoint.update(question?.id!!, updateQuestion)
                        .then(() => navigate('/questions'));
                }}><Icon icon={IconsConstant.CHECK}/></Button>
                <Button theme="primary error" onClick={() => {
                    QuestionEndpoint.delete(question.id)
                        .then(() => navigate('/questions'));
                }}><Icon icon={IconsConstant.CLOSE}/></Button>
            </HorizontalLayout>
            <HorizontalLayout className={styles.question_content}>
                <VerticalLayout style={{width: '100%'}}>
                    <TextArea value={question.question}
                              className={styles.question_text_area}
                              onChange={(e) => {
                                  setQuestion(prev => prev ? {...prev, question: e.target.value} : undefined);
                              }}
                    />
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
                    <AnswersTable questionId={question.id}/>
                </VerticalLayout>
            </HorizontalLayout>

        </>
    );
}
