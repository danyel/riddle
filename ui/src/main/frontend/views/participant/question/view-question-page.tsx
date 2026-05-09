import {useParams} from "react-router";
import Question from "Frontend/generated/be/riddler/v1/question/api/Question";
import {useEffect, useState} from "react";
import {ParticipantEndpoint} from "Frontend/generated/endpoints";

function ViewQuestionPage() {
    const [question, setQuestion] = useState<Question>();
    const params = useParams();
    const questionId = params.id;

    useEffect(() => {
        ParticipantEndpoint.get(questionId)
            .then(setQuestion);
    }, [questionId])

    console.log(questionId);
    return (
        <>
            {question?.question}

            {question?.single_choice && (<>single</>)}
            {question?.multiple_choice && (<>multiple</>)}
            {question?.open && (<>Open</>)}
        </>
    );
}

export default ViewQuestionPage;
