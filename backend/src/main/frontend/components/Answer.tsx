import {useEffect, useState} from "react";
import {AnswerService} from "Frontend/generated/endpoints";
import Answer from "Frontend/generated/be/riddler/answer/bff/Answer";

export interface AnswerProperties {
    id: string;
}

export function Answers(answerProperties: AnswerProperties) {
    const [answers, setAnswers] = useState<Answer[]>([]);

    useEffect(() => {
        AnswerService.findByQuestion(answerProperties.id).then(setAnswers);
    }, []);

    return (
        <div>
            {answers.map(answer => (<div>{answer.id} {answer.value}</div>))}
        </div>
    );
}
