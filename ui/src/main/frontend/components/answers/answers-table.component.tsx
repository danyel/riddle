import {useEffect, useState} from "react";
import {Dialog, Grid, GridColumn, HorizontalLayout, TextArea} from "@vaadin/react-components";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import {AnswerEndpoint} from "Frontend/generated/endpoints";
import Answer from "Frontend/generated/be/riddler/v1/answer/api/Answer";
import {useSignal} from "@vaadin/hilla-react-signals";
import CreateAnswer from "Frontend/generated/be/riddler/v1/answer/api/CreateAnswer";
import {CancelButton, CheckButton, CloseButton, PlusButton} from "Frontend/components/ui/button";

export interface AnswersTableProperties {
    questionId: string;
}

export default function AnswersTable(props: AnswersTableProperties) {
    const [answers, setAnswers] = useState<Answer[]>([]);
    const [open, setOpen] = useState(false);

    const fetchAnswers = () => {
        AnswerEndpoint.findByQuestion(props.questionId)
            .then(setAnswers);
    };

    useEffect(() => {
        fetchAnswers();
    }, [props.questionId]);

    return (
        <>
            <HorizontalLayout className={styles.answers_menu_bar}>
                <PlusButton onClick={() => setOpen(true)}/>
            </HorizontalLayout>
            {/* 1. We pass down a close handler to reset parent state */}
            <CreateDialogModal
                show={open}
                questionId={props.questionId}
                onAnswerCreated={fetchAnswers}
                onClose={() => setOpen(false)}
            />
            <Grid items={answers} className={styles.question_table} allRowsVisible={true}>
                <GridColumn path="value" header="Answer Value"/>
            </Grid>
        </>
    );
}

interface CreateDialogModalProps {
    show: boolean;
    questionId: string;
    onAnswerCreated: () => void;
    onClose: () => void;
}

function CreateDialogModal(props: CreateDialogModalProps) {
    const answerValue = useSignal('');

    useEffect(() => {
        if (props.show) {
            // Clear input value when the modal opens
            answerValue.value = '';
        }
    }, [props.show]);

    function saveAnswer() {
        const payload: CreateAnswer = {question_id: props.questionId, value: answerValue.value};
        AnswerEndpoint.create(payload)
            .then(() => {
                props.onAnswerCreated();
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
                    <CheckButton onClick={saveAnswer}/>
                </>
            )}
        >
            <div className={styles.div_full}>
                <TextArea
                    label="Name"
                    value={answerValue.value}
                    onValueChanged={(e) => (answerValue.value = e.detail.value)}
                    className={styles.text_area_full}
                />
            </div>
        </Dialog>
    );
}
