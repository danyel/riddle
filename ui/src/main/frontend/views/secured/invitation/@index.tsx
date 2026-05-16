import {useParams} from "react-router";

export default function InvitationPage() {
    const params = useParams();
    return (<>
        {params.invitation_id}
        {params.participant_id}
    </>);
}