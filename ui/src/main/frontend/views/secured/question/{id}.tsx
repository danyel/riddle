import {useParams} from "react-router";
import {useEffect, useState} from "react";
import {QuestionEndpoint, QuestionTypeEndpoint, TranslateEndpoint} from "Frontend/generated/endpoints";
import Question from "Frontend/generated/be/riddler/v1/question/api/Question";
import AnswersTable from "Frontend/components/answers/answers-table.component";
import {Button, HorizontalLayout, Select} from "@vaadin/react-components";
// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';
import QuestionType from "Frontend/generated/be/riddler/v1/question/api/QuestionType";
import UpdateQuestion from "Frontend/generated/be/riddler/v1/question/api/UpdateQuestion";


export default function QuestionDetailView() {
    const [question, setQuestion] = useState<Question>();
    const [translatedType, setTranslatedType] = useState<string>('');
    const [items, setItems] = useState<{ label: string, value: string }[]>([]);
    const {id} = useParams();

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

    const translate = (language: string, key: string): string => {
        let translation = '';
        TranslateEndpoint.translate(language, key)
            .then(e => {
                translation = e.value;
                console.log(translation);
            });
        return translation;
    }
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

    function updateQuestion() {
        const updateQuestion: UpdateQuestion = {question: question?.question!!, type: question?.type!!};
        QuestionEndpoint.update(question?.id!!, updateQuestion)
            .then(setQuestion);
    }

    return question && (<>
            <HorizontalLayout className={styles.question_menu_bar}>
                <Button theme="primary">Save</Button>
                <Button theme="primary" onClick={() => updateQuestion()}>Update</Button>
                <Button theme="primary">Save</Button>
                <Button theme="primary">Save</Button>
            </HorizontalLayout>
            <HorizontalLayout style={{width: "100%"}}>
                <div>
                    {question.question}
                </div>
                <div>
                    question type {translatedType || 'Loading...'} {question.type}

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
                </div>
            </HorizontalLayout>
            <AnswersTable questionId={question.id}/>
        </>
    );
}
