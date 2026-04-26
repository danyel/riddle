import {useParams} from "react-router";
import {useEffect, useState} from "react";
import {QuestionService} from "Frontend/generated/endpoints";
import Question from "Frontend/generated/be/riddler/domain/question/api/Question";
import AnswersTable from "Frontend/components/answers/answers-table.component";
import {ViewConfig} from "@vaadin/hilla-file-router/types.js";

export const config: ViewConfig = {
    loginRequired: true, // Forces redirect to /login if not authenticated
    // rolesAllowed: ['ADMIN'] // Optional: limit to specific roles
};

export default function GetQuestion() {
    const [question, setQuestion] = useState<Question>();
    const {id} = useParams();

    useEffect(() => {
        QuestionService.get(id).then(setQuestion);
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
