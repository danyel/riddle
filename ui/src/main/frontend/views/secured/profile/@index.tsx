import {useSettingsState} from "../settings-context-provider";

export default function ProfilePage() {
    const {settings} = useSettingsState();

    return (
        <>
            {settings.username}
            {settings.roles?.map((role) => (<span key={role}>{role}</span>))}
        </>
    );
}