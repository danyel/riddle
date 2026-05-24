import {useEffect, useState} from "react";
import Question from "Frontend/generated/be/riddler/v1/question/client/model/Question";
import {ParticipantEndpoint} from "Frontend/generated/endpoints";
import {ViewDetailButton} from "Frontend/components/ui/button";
import {Navigate} from "Frontend/util/navigate";

export default function ParticipantPage() {
    const [questions, setQuestions] = useState<Question[]>([]);

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
                                      onClick={() => Navigate.path(`/participant/question/${question.id}`)}/>
                );
            })}
        </div>
    );
}