import {useSettingsState} from "../settings-context-provider";
import {Dialog, Grid, GridColumn} from "@vaadin/react-components";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import {DeleteButton} from "Frontend/components";
import Bookmark from "Frontend/generated/be/riddler/v1/settings/model/Bookmark";
import {BookmarkEndpoint} from "Frontend/generated/endpoints";
import {Button} from "@vaadin/react-components/Button";
import {ElementStylingTypes} from "Frontend/constant";
import {useState} from "react";
import {useSignal} from "@vaadin/hilla-react-signals";

export default function ProfilePage() {
    const {settings, setSettings} = useSettingsState();
    const [bookmark, setBookmark] = useState<Bookmark>()
    const dialogOpened = useSignal(false);
    const bookmarksActions = ({model}: { model: { item: Bookmark } }) => {
        return (
            <DeleteButton
                onClick={() => {
                    setBookmark(model.item);
                    dialogOpened.value = true;
                }}
            />
        );
    };

    function removeBookmark() {
        BookmarkEndpoint.deleteBookmark(bookmark!!)
            .then(() => {
                    dialogOpened.value = false;
                    setSettings({
                        ...settings,
                        bookmarks: settings.bookmarks.filter((e: Bookmark) => e && e.id !== bookmark!.id)
                    });
                }
            );
    }

    return (
        <>
            {settings.username}
            {settings.roles?.map((role) => (<span key={role}>{role}</span>))}
            <Dialog
                headerTitle={`Delete bookmark "${bookmark?.path} ${bookmark?.label}"?`}
                opened={dialogOpened.value}
                onClosed={() => {
                    dialogOpened.value = false;
                }}
                footer={
                    <>
                        <Button theme={ElementStylingTypes.PRIMARY_ERROR} onClick={removeBookmark}
                                style={{marginRight: 'auto'}}>
                            Delete
                        </Button>
                        <Button theme="tertiary" onClick={close}>
                            Cancel
                        </Button>
                    </>
                }
            >
                Are you sure you want to delete this user permanently?
            </Dialog>
            <Grid items={settings.bookmarks} className={styles.riddler_table} allRowsVisible={true}>
                <GridColumn path="bookmarkType" header="Bookmark type"/>
                <GridColumn path="path" header="Path"/>
                <GridColumn path="label" header="label"/>
                <GridColumn header={'Action'} renderer={bookmarksActions}/>
            </Grid>
        </>
    );
}