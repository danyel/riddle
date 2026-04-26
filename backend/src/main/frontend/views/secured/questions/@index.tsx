import {useEffect, useState} from 'react';
import {ComponentService, QuestionService} from "Frontend/generated/endpoints";
import {ViewConfig} from '@vaadin/hilla-file-router/types.js';
import {Button, Grid, GridColumn, Icon} from "@vaadin/react-components";
// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';

// CRITICAL: You must import the iconset for the icons to render
import '@vaadin/icons';
import {useNavigate} from "react-router";
import {IconsConstant} from "Frontend/constant/constants";
import Question from "Frontend/generated/be/riddler/domain/question/api/Question";

export default function Index() {
    const [items, setItems] = useState<string[]>([]);
    const [questions, setQuestions] = useState<Question[]>([]);
    const navigate = useNavigate();
    useEffect(() => {
        QuestionService.getQuestions().then(setQuestions);
        ComponentService.icons().then(setItems);
    }, []);

    const handleClick = (question: Question) => {
        navigate(`/secured/question/${question.id}`);
    }

    const answerRenderer = ({item}: { item: Question }) => {
        return <Button theme="primary" onClick={() => handleClick(item)}><Icon icon={IconsConstant.EYE}/> </Button>;
    };

    return (
        <Grid key={"id"} items={questions} className={styles.question_table} allRowsVisible={true}>
            <GridColumn key={"id"} path={"id"}/>
            <GridColumn key={"question"} path={"question"}/>
            <GridColumn key={"type"} path={"type"}/>
            <GridColumn header={'Action'} renderer={answerRenderer}/>
        </Grid>
    );
}


export const config: ViewConfig = {
    title: 'Questions',
    loginRequired: true
};