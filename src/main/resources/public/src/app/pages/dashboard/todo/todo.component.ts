import { Component, ViewEncapsulation } from '@angular/core';
import { BaThemeConfigProvider } from '../../../theme';

import { TodoService, Todo } from './';

@Component({
    selector: 'todo',
    encapsulation: ViewEncapsulation.None,
    styles: [require('./todo.scss')],
    template: require('./todo.html')
})
export class TodoComponent {

    public dashboardColors: any;

    public todoList: Todo[];
    public newTodoText: string = '';

    constructor(private _baConfig: BaThemeConfigProvider, private _todoService: TodoService) {
        let that = this;
        this.dashboardColors = this._baConfig.get().colors.dashboard;
        this._todoService.getTodoList().subscribe(todos => that.todoList = todos);

        this.todoList.forEach((item) => {
            item.color = this._getRandomColor();
        });
    }

    addToDoItem($event) {
        if (($event.which === 1 || $event.which === 13) && this.newTodoText.trim() != '') {
            let newTodo: Todo = new Todo();
            newTodo.text = this.newTodoText;
            newTodo.color = this._getRandomColor();
            newTodo.finished = false;
            this._todoService.post(newTodo)
                .subscribe(todo => this.todoList.unshift(todo));
            this.newTodoText = '';
        }
    }

    updateFinishedItem(todo: Todo) {
        let that = this;
        todo.finished = !todo.finished;
        this._todoService.put(todo)
            .subscribe(updated => {
                let toDelete: number = -1;
                that.todoList.forEach(function(item, index) {
                    if (item.id === todo.id) {
                        toDelete = index;
                    }
                });
                if (toDelete > -1) {
                    that.todoList.splice(toDelete, 1, updated);
                }
            });
    }

    deleteItem(todo: Todo) {
        let that = this;
        this._todoService.delete(todo)
            .subscribe(isOk => {
                if (isOk === true) {
                    let toDelete: number = -1;
                    that.todoList.forEach(function(item, index) {
                        if (item.id === todo.id) {
                            toDelete = index;
                        }
                    });
                    if (toDelete > -1) {
                        that.todoList.splice(toDelete);
                    }
                }
            });
    }


    private _getRandomColor() {
        let colors = Object.keys(this.dashboardColors).map(key => this.dashboardColors[key]);

        var i = Math.floor(Math.random() * (colors.length - 1));
        return colors[i];
    }
}
