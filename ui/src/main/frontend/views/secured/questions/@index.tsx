import {useEffect, useState} from 'react';
import {Dialog, Grid, GridColumn, HorizontalLayout, Select, TextField} from "@vaadin/react-components";
// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';

// CRITICAL: You must import the iconset for the icons to render
import '@vaadin/icons';
import {useNavigate} from "react-router";
import Question from "Frontend/generated/be/riddler/v1/question/domain/Question";
import CreateQuestion from "Frontend/generated/be/riddler/v1/question/domain/CreateQuestion";
import {QuestionEndpoint} from "Frontend/generated/endpoints";
import {CancelButton, CheckButton, CloseButton, PlusButton, ViewDetailButton} from "Frontend/components/ui/button";
import {useSignal} from "@vaadin/hilla-react-signals";
import QuestionType from "Frontend/generated/be/riddler/v1/question/domain/QuestionType";


function CreateQuestionDialogModal(props: {
    show: boolean;
    onParticipantCreated: () => void;
    onClose: () => void;
}) {
    const createQuestion = useSignal<CreateQuestion>({type: QuestionType.OPEN, question: ''});
    const dropDown: { label: string, value: QuestionType }[] = [
        {
            label: 'Open',
            value: QuestionType.OPEN
        },
        {
            label: 'Review',
            value: QuestionType.REVIEW
        },
        {
            label: 'Single Choice',
            value: QuestionType.SINGLE_CHOICE
        },
        {
            label: 'Multiple Choice',
            value: QuestionType.MULTIPLE_CHOICE
        }
    ];

    useEffect(() => {
        if (props.show) {
            // Clear input value when the modal opens
            createQuestion.value.type = QuestionType.OPEN;
            createQuestion.value.question = '';
        }
    }, [props.show]);

    function saveQuestion() {
        const payload: CreateQuestion = {
            question: createQuestion.value.question,
            type: createQuestion.value.type,
        }
        QuestionEndpoint.create(payload)
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
            header-title="Create question"
            opened={props.show}
            onOpenedChanged={closeIfNotValue}
            header={<CancelButton onClick={() => props.onClose()}/>}
            footerRenderer={() => (
                <>
                    <CloseButton onClick={props.onClose}/>
                    <CheckButton onClick={saveQuestion}/>
                </>
            )}
        >

            <div>
                <TextField
                    label="Last Name"
                    value={createQuestion.value.question}
                    onValueChanged={(e) => (createQuestion.value.question = e.detail.value)}
                    className={styles.text_area_full}
                />
            </div>

            <div>
                <Select label="Sort by" items={dropDown} value={createQuestion.value.type}/>
            </div>
        </Dialog>
    );
}

export default function QuestionsView() {
    const [open, setOpen] = useState(false);
    const [questions, setQuestions] = useState<Question[]>([]);
    const navigate = useNavigate();

    function fetchQuestions() {
        QuestionEndpoint.getQuestions().then(setQuestions);
    }

    useEffect(() => {
        fetchQuestions();
    }, []);

    const showQuestion = ({item}: { item: Question }) => {
        return <ViewDetailButton onClick={() => navigate(`/question/${item.id}`)}/>;

    };

    return (
        <>
            <HorizontalLayout className={styles.answers_menu_bar}>
                <PlusButton onClick={() => setOpen(true)}/>
            </HorizontalLayout>
            <CreateQuestionDialogModal show={open}
                                       onParticipantCreated={fetchQuestions}
                                       onClose={() => setOpen(false)}/>
            <Grid key={"id"} items={questions} className={styles.riddler_table} allRowsVisible={true}>
                <GridColumn key={"question"} path={"question"}/>
                <GridColumn key={"type"} path={"type"}/>
                <GridColumn header={'Action'} renderer={showQuestion}/>
            </Grid>

        </>
    );
}