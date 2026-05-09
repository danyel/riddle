import {useEffect, useState} from "react";
import Question from "Frontend/generated/be/riddler/v1/question/api/Question";
import {useNavigate} from "react-router";
import {ParticipantEndpoint} from "Frontend/generated/endpoints";
import {ViewDetailButton} from "Frontend/components/ui/button";

export default function ParticipantPage() {
    const [questions, setQuestions] = useState<Question[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        ParticipantEndpoint.getQuestions()
            .then(setQuestions);
    });

    return (
        <div>
            Participant

            {questions.map((question: Question) => {
                return (
                    <ViewDetailButton key={question.id}
                                      onClick={() => navigate(`/participant/question/${question.id}`)}/>
                );
            })}
        </div>
    );
}