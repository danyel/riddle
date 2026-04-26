import {useParams} from "react-router";
import {useEffect, useState} from "react";
import Question from "Frontend/generated/be/riddler/question/bff/Question";
import {QuestionService} from "Frontend/generated/endpoints";


export default function GetQuestion() {
    const [question, setQuestion] = useState<Question>();
    const {id} = useParams();

    useEffect(() => {
        QuestionService.get(id).then(setQuestion);
    }, [id]);

    return question && (
        <div>
            {question.question}
        </div>
    );
}
