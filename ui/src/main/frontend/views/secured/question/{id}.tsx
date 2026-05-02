import {useParams} from "react-router";
import {useEffect, useState} from "react";
import {QuestionEndpoint} from "Frontend/generated/endpoints";
import Question from "Frontend/generated/be/riddler/v1/question/api/Question";
import AnswersTable from "Frontend/components/answers/answers-table.component";


export default function QuestionDetailView() {
    const [question, setQuestion] = useState<Question>();
    const {id} = useParams();

    useEffect(() => {
        console.log(`Trying to fetch ${id}`);
        QuestionEndpoint.get(id).then(setQuestion);
    }, [id]);

    return question && (
        <div>
            {question.question}
            multipleChoice {question.multiple_choice}
            singleChoice {question.single_choice}
            open {question.open}
            <AnswersTable questionId={question.id}/>
        </div>
    );
}
