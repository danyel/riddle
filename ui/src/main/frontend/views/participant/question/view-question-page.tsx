import {useNavigate, useParams} from "react-router";
import Question from "Frontend/generated/be/riddler/v1/question/api/Question";
import {useEffect, useState} from "react";
import {ParticipantEndpoint} from "Frontend/generated/endpoints";
import {useActivityTracker} from "Frontend/views/participant/useActivityTracker";
import {TextArea} from "@vaadin/react-components";
import {useSignal} from "@vaadin/hilla-react-signals";

function ViewQuestionPage() {
    const [question, setQuestion] = useState<Question>();
    const params = useParams();
    const questionId = params.id;
    const answerValue = useSignal('');
    const navigate = useNavigate();

    if (!questionId) {
        navigate('/error/404');
    }

    // todo if questionId is null than go to page not found
    const {track} = useActivityTracker(questionId!!);

    useEffect(() => {
        ParticipantEndpoint.get(questionId)
            .then(setQuestion);
    }, [questionId])

    return (
        <>
            {question?.question}

            {question?.single_choice && (<>single</>)}
            {question?.multiple_choice && (<>multiple</>)}
            {question?.open && (<TextArea
                label="Your Answer"
                value={answerValue.value}
                // Track focus changes to understand user hesitation/interaction times
                onValueChanged={(e) => {
                    answerValue.value = e.detail.value;
                    // Debounce typing tracking or capture single state shifts
                    track('INPUT', 'textarea-answer', e.detail.value);
                }}
                style={{width: '100%', minHeight: '200px'}}
            />)}
        </>
    );
}

export default ViewQuestionPage;
