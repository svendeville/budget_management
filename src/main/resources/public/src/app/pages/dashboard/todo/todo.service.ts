import {Injectable} from "@angular/core";
import {Http, RequestOptions} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Todo} from "./";
import {SerializationHelper} from "./../../../helper/serialization-helper";
import * as AppConst from "./../../../core/app.const";
import {environment} from "../../../../environments/environment";

@Injectable()
export class TodoService {

  constructor(private _http: Http) {
  }

  private _todoList: Todo[];

  public getTodoList(): Observable<Todo[]> {
    let that = this;
    this._todoList = new Array();
    return this._http.get(AppConst.BACKEND_API_ROOT_URL + "/todos")
      .map(resp => <Object[]>resp.json())
      .map(jsonTodos => {
        jsonTodos.forEach(jsonTodo => that._todoList.push(SerializationHelper.toInstanceFromJsonObj(new Todo(), jsonTodo)));
        return that._todoList;
      });
  }

  public post(todo: Todo): Observable<Todo> {
    return this._http.post(AppConst.BACKEND_API_ROOT_URL + "/todos", JSON.stringify(todo), environment.PROPERTIES["HOST_OPTIONS"])
      .map(resp => SerializationHelper.toInstanceFromJsonObj(new Todo(), resp.json()));
  }

  public put(todo: Todo): Observable<Todo> {
    return this._http.put(AppConst.BACKEND_API_ROOT_URL + "/todos", JSON.stringify(todo), environment.PROPERTIES["HOST_OPTIONS"])
      .map(resp => SerializationHelper.toInstanceFromJsonObj(new Todo(), resp.json()));
  }

  public delete(todo: Todo): Observable<boolean> {
    let requestOptions: RequestOptions = new RequestOptions();
    requestOptions.merge(environment.PROPERTIES["HOST_OPTIONS"]);
    requestOptions.body = JSON.stringify(todo);
    return this._http.delete(AppConst.BACKEND_API_ROOT_URL + "/todos/" + todo.id, requestOptions)
      .map(resp => resp.json());
  }
}
