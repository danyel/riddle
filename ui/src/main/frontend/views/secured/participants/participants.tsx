import Participant from "Frontend/generated/be/riddler/v1/participant/client/model/Participant";
import {Button, FormLayout, Grid, GridColumn, HorizontalLayout, TextField} from "@vaadin/react-components";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import {useEffect, useState} from "react";
import {ParticipantAdminEndpoint} from "Frontend/generated/endpoints";
import {useSignal} from "@vaadin/hilla-react-signals";
import {CheckButton, CloseButton, GenerateToken, ViewDetailButton} from "Frontend/components/ui/button";
import CreateParticipant from "Frontend/generated/be/riddler/v1/participant/client/model/CreateParticipant";
import {CheckIcon, CloseIcon} from "Frontend/components/ui/icons";
import {useNavigate} from "react-router";
import {Notify, Strings, Urls} from "Frontend/util";
import BookmarkType from "Frontend/generated/be/riddler/v1/settings/model/BookmarkType";
import RiddlerModal from "Frontend/components/ui/modal/modal";
import FormItem from "Frontend/components/ui/form/form-item.component";
import Empty from "Frontend/components/ui/empty/empty";

function ParticipantTable() {
    const [open, setOpen] = useState(false);
    const [participants, setParticipants] = useState<Participant[]>([]);
    const navigate = useNavigate();

    function fetchParticipants() {
        ParticipantAdminEndpoint.findAll()
            .then(setParticipants);
    }

    useEffect(() => {
        fetchParticipants();
    }, []);

    const actionButtons = ({item}: { item: Participant }) => {
        return (
            <>
                <GenerateToken onClick={() => ParticipantAdminEndpoint.generateToken(item.id).then(_ => {
                    fetchParticipants();
                    Notify.success('Token generated successfully for {} {}', item.first_name, item.last_name);
                })}/>
                <ViewDetailButton onClick={() => {
                    navigate(Urls.makePath(BookmarkType.PARTICIPANTS, item.id));
                }}/>
            </>
        );
    };

    const tokenIndicator = ({item}: { item: Participant }) => {
        return (
            <>
                {Strings.isNotEmpty(item.stored_token) && (<CheckIcon/>)}
                {Strings.isEmpty(item.stored_token) && (<CloseIcon/>)}
            </>
        );
    };

    return (
        <>
            <HorizontalLayout className={styles.full_width_layout}>
                <div className={styles.menu_bar_layout}>
                    <Button theme="primary" onClick={() => setOpen(true)}>Add New Participant</Button>
                </div>
            </HorizontalLayout>
            <CreateParticipantDialogModal show={open}
                                          onParticipantCreated={fetchParticipants}
                                          onClose={() => setOpen(false)}/>
            {participants.length > 0 ? (
                <HorizontalLayout className={styles.full_width_layout}>
                    <Grid key={"id"} items={participants} className={styles.riddler_table} allRowsVisible={true}>
                        <GridColumn key={"first_name"} header={'First Name'} path={"first_name"}/>
                        <GridColumn key={"last_name"} header={'Last Name'} path={"last_name"}/>
                        <GridColumn key={"email_address"} header={'Email'} path={"email_address"}/>
                        <GridColumn key={"stored_token"} header={'Token'} renderer={tokenIndicator}/>
                        <GridColumn header={'Actions'} renderer={actionButtons}/>
                    </Grid>
                </HorizontalLayout>
            ) : (
                <Empty emptyMessage={"No participant found"}
                       helperMessage={"Click \"Add New Participant\" to add a new participant."}/>
            )}
        </>
    );
}

function CreateParticipantDialogModal(props: {
    show: boolean;
    onParticipantCreated: () => void;
    onClose: () => void;
}) {
    const createParticipant = useSignal<CreateParticipant>({});

    useEffect(() => {
        if (props.show) {
            // Clear input value when the modal opens
            createParticipant.value.email_address = '';
            createParticipant.value.first_name = '';
            createParticipant.value.last_name = '';
        }
    }, [props.show]);

    function saveParticipant() {
        const payload: CreateParticipant = {
            email_address: createParticipant.value.email_address,
            first_name: createParticipant.value.first_name,
            last_name: createParticipant.value.last_name,
        }
        ParticipantAdminEndpoint.create(payload)
            .then(() => {
                props.onParticipantCreated();
                props.onClose();
            });
    }

    return (
        <RiddlerModal
            headerTitle="Add participant"
            opened={props.show}
            onClosed={() => {
                props.onClose()
            }}
            footer={
                <>
                    <CheckButton onClick={saveParticipant}/>
                    <CloseButton onClick={props.onClose}/>
                </>
            }
            content={
                <>
                    <FormLayout style={{width: '100%'}}
                                autoResponsive
                                columnWidth="8em"
                                expandColumns
                                expandFields>
                        <FormItem key={"firstName"}
                                  children={
                                      <TextField
                                          label="First Name"
                                          value={createParticipant.value.first_name}
                                          onValueChanged={(e) => (createParticipant.value.first_name = e.detail.value)}
                                          className={styles.text_full}
                                      />
                                  }/>
                        <FormItem key={"lastName"}
                                  children={
                                      <TextField
                                          label="Last Name"
                                          value={createParticipant.value.last_name}
                                          onValueChanged={(e) => (createParticipant.value.last_name = e.detail.value)}
                                          className={styles.text_full}
                                      />
                                  }/>
                        <FormItem key={"emailAddress"}
                                  children={
                                      <TextField
                                          label="Email"
                                          value={createParticipant.value.email_address}
                                          onValueChanged={(e) => (createParticipant.value.email_address = e.detail.value)}
                                          className={styles.text_full}
                                      />
                                  }/>
                    </FormLayout>

                </>
            }
        />
    );
}

export default function ParticipantsPage() {
    return (
        <>
            <ParticipantTable/>
        </>
    );
}