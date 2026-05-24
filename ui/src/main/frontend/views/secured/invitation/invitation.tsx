import {useParams} from "react-router";
import {useEffect, useState} from "react";
import {InvitationEndpoint} from "Frontend/generated/endpoints";
import Invitation from "Frontend/generated/be/riddler/v1/invitation/client/model/Invitation";
import {Notify} from "Frontend/util";

export default function InvitationPage() {
    const params = useParams();
    const [invitation, setInvitation] = useState<Invitation>();

    useEffect(() => {
        if (params.invitation_id) {
            InvitationEndpoint.findById(params.invitation_id)
                .then(setInvitation)
                .catch(() => Notify.error("No invitation found for {}.", params.invitation_id));
        }
    }, [params.invitation_id]);

    return (<>
        {params.invitation_id}
    </>);
}