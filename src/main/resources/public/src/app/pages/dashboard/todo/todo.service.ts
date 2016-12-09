import {Injectable} from '@angular/core';

@Injectable()
export class TodoService {

  private _todoList = [
    { text: 'Check me out', color:"", finished:false, deleted:false },
    { text: 'Lorem ipsum dolor sit amet, possit denique oportere at his, etiam corpora deseruisse te pro', color:"", finished:false, deleted:false },
    { text: 'Ex has semper alterum, expetenda dignissim', color:"", finished:false, deleted:false },
    { text: 'Vim an eius ocurreret abhorreant, id nam aeque persius ornatus.', color:"", finished:false, deleted:false },
    { text: 'Simul erroribus ad usu', color:"", finished:false, deleted:false },
    { text: 'Ei cum solet appareat, ex est graeci mediocritatem', color:"", finished:false, deleted:false },
    { text: 'Get in touch with akveo team', color:"", finished:false, deleted:false },
    { text: 'Write email to business cat', color:"", finished:false, deleted:false },
    { text: 'Have fun with blur admin', color:"", finished:false, deleted:false },
    { text: 'What do you think?', color:"", finished:false, deleted:false },
  ];

  getTodoList() {
    return this._todoList;
  }
}
