import {useEffect, useState} from "react";
import {AnswerEndpoint} from "Frontend/generated/endpoints";
import Answer from "Frontend/generated/be/riddler/v1/answer/client/model/Answer";

export interface AnswerProperties {
    id: string;
}

export function Answers(answerProperties: AnswerProperties) {
    const [answers, setAnswers] = useState<Answer[]>([]);

    useEffect(() => {
        AnswerEndpoint.findByQuestion(answerProperties.id).then(setAnswers);
    }, []);

    return (
        <div>
            {answers.map(answer => (<div>{answer.id} {answer.value}</div>))}
        </div>
    );
}
