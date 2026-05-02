import {useEffect, useState} from 'react';
import {Button, Grid, GridColumn, Icon} from "@vaadin/react-components";
// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';

// CRITICAL: You must import the iconset for the icons to render
import '@vaadin/icons';
import {useNavigate} from "react-router";
import {IconsConstant} from "Frontend/constant/constants";
import Question from "Frontend/generated/be/riddler/v1/question/api/Question";
import {QuestionEndpoint} from "Frontend/generated/endpoints";

export default function QuestionsView() {
    const [questions, setQuestions] = useState<Question[]>([]);
    const navigate = useNavigate();
    useEffect(() => {
        QuestionEndpoint.getQuestions().then(setQuestions);
    }, []);

    const handleClick = (question: Question) => {
        console.log(`Click handle executed for question ${question.id}`);
        navigate(`/question/${question.id}`);
    }

    const answerRenderer = ({item}: { item: Question }) => {
        return <Button theme="primary" onClick={() => handleClick(item)}><Icon icon={IconsConstant.EYE}/> </Button>;
    };

    return (
        <Grid key={"id"} items={questions} className={styles.question_table} allRowsVisible={true}>
            <GridColumn key={"question"} path={"question"}/>
            <GridColumn key={"type"} path={"type"}/>
            <GridColumn header={'Action'} renderer={answerRenderer}/>
        </Grid>
    );
}