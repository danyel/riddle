import ParticipantDetail from "Frontend/generated/be/riddler/v1/participant/domain/ParticipantDetail";
import {Dialog, Grid, GridColumn, HorizontalLayout, TextField} from "@vaadin/react-components";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import {useEffect, useState} from "react";
import {ParticipantAdminEndpoint} from "Frontend/generated/endpoints";
import {useSignal} from "@vaadin/hilla-react-signals";
import {CancelButton, CheckButton, CloseButton, GenerateToken, PlusButton} from "Frontend/components/ui/button";
import CreateParticipant from "Frontend/generated/be/riddler/v1/participant/domain/CreateParticipant";
import {CheckIcon, CloseIcon} from "Frontend/components/ui/icons";

function ParticipantTable() {
    const [open, setOpen] = useState(false);
    const [participants, setParticipants] = useState<ParticipantDetail[]>([]);

    function fetchParticipants() {
        ParticipantAdminEndpoint.findAll()
            .then(setParticipants);
    }

    useEffect(() => {
        fetchParticipants();
    }, []);

    const tokenGeneration = ({item}: { item: ParticipantDetail }) => {
        return <GenerateToken onClick={() => ParticipantAdminEndpoint.generateToken(item.id).then(e => {
            fetchParticipants();
        })}/>;
    };

    const tokenIndicator = ({item}: { item: ParticipantDetail }) => {
        return (
            <>
                {item.stored_token && (<CheckIcon/>)}
                {!item.stored_token && (<CloseIcon/>)}
            </>
        );
    };

    return (
        <>
            <HorizontalLayout className={styles.answers_menu_bar}>
                <PlusButton onClick={() => setOpen(true)}/>
            </HorizontalLayout>
            <CreateParticipantDialogModal show={open}
                                          onParticipantCreated={fetchParticipants}
                                          onClose={() => setOpen(false)}/>
            <Grid key={"id"} items={participants} className={styles.riddler_table} allRowsVisible={true}>
                <GridColumn key={"first_name"} header={'First Name'} path={"first_name"}/>
                <GridColumn key={"last_name"} header={'Last Name'} path={"last_name"}/>
                <GridColumn key={"email_address"} header={'Email'} path={"email_address"}/>
                <GridColumn key={"stored_token"} header={'Token'} renderer={tokenIndicator}/>
                <GridColumn header={'Actions'} renderer={tokenGeneration}/>
            </Grid>
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

    function closeIfNotValue(e: CustomEvent<{ value: boolean }>) {
        // Syncs background clicks / ESC keys directly back to parent
        if (!e.detail.value) props.onClose();
    }

    return (
        <Dialog
            width={"100vh"}
            height={"100vh"}
            header-title="Create answer"
            opened={props.show}
            onOpenedChanged={closeIfNotValue}
            header={<CancelButton onClick={() => props.onClose()}/>}
            footerRenderer={() => (
                <>
                    <CloseButton onClick={props.onClose}/>
                    <CheckButton onClick={saveParticipant}/>
                </>
            )}
        >
            <div className={styles.div_full}>
                <TextField
                    label="First Name"
                    value={createParticipant.value.email_address}
                    onValueChanged={(e) => (createParticipant.value.first_name = e.detail.value)}
                    className={styles.text_area_full}
                />
            </div>

            <div className={styles.div_full}>
                <TextField
                    label="Last Name"
                    value={createParticipant.value.email_address}
                    onValueChanged={(e) => (createParticipant.value.last_name = e.detail.value)}
                    className={styles.text_area_full}
                />
            </div>

            <div className={styles.div_full}>
                <TextField
                    label="Email"
                    value={createParticipant.value.email_address}
                    onValueChanged={(e) => (createParticipant.value.email_address = e.detail.value)}
                    className={styles.text_area_full}
                />
            </div>
        </Dialog>
    );
}

export default function ParticipantsPage() {
    return (
        <>
            <ParticipantTable/>
        </>
    );
}