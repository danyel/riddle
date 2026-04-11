import {useEffect, useState} from 'react';

import {Grid} from '@vaadin/react-components/Grid';
import {GridColumn} from '@vaadin/react-components/GridColumn';
import {Button} from '@vaadin/react-components/Button';
import {QuestionService} from "Frontend/generated/endpoints";
import Question from "Frontend/generated/be/riddler/question/bff/Question";


export default function MainView() {
    const [products, setProducts] = useState<Question[]>([]);

    useEffect(() => {
        QuestionService.getQuestions().then(setProducts);
    }, []);

    return (
        <div style={{padding: '1rem'}}>
            <h1>Products</h1>

            <Button onClick={() => alert('Clicked!')}>
                Click Me
            </Button>

            <Grid items={products}>
                <GridColumn path="id" header="Id"/>
                <GridColumn path="question" header="Question"/>
                <GridColumn path="type" header="Type"/>
            </Grid>
        </div>
    );
}