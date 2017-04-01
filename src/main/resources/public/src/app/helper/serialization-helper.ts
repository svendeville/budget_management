/**
 * Class used to manage json to typescript object deserialisation
 * copy each property from json object to property instance
 * or if method fromJson provided use it to deserialize
 * adding method
 * usage :
 * var json = '{"name": "John Doe"}',
 * foo = SerializationHelper.toInstance(new Foo(), json);
 * foo.GetName() === "John Doe";
 */
export class SerializationHelper {
  /**
   * Json String to Instance of type T
   * @param obj
   * @param json
   * @returns {T}
   */
  static toInstance<T>(obj: T, json: string): T {
    var jsonObj = JSON.parse(json);
    return SerializationHelper.toInstanceFromJsonObj(obj, jsonObj);
  }


  /**
   * Json object to Instance of type T
   * @param obj
   * @param json
   * @returns {T}
   */
  static toInstanceFromJsonObj<T>(obj: T, jsonObj: Object): T {

    if (jsonObj === null) {
      return null;
    }

    if (typeof obj["fromJSON"] === "function") {
      obj["fromJSON"](jsonObj);
    }
    else {
      SerializationHelper.copyProperties(obj, jsonObj);
    }

    return obj;
  }

  /**
   * array of Json objects to Instance of type T array
   * @param obj instance
   * @param jsonObjArray
   * @returns {T}
   */
  static toArrayInstanceFromJsonObjArray<T>(obj: T, jsonObjArray: Array<Object>): Array<T> {

    if (jsonObjArray === null) {
      return null;
    }

    return jsonObjArray.map(item => {
      var cloneObj = new (<any>obj.constructor)();
      return SerializationHelper.toInstanceFromJsonObj(cloneObj, item);
    });
  }

  /**
   * Copy all properties value from jsonObj or specified property names into obj
   * @param obj
   * @param jsonObj
   * @param propertyNames
   * @return hydrated object obj
   */
  static copyProperties<T>(obj: T, jsonObj: Object, propertyNames?: string[]): T {
    if (propertyNames) {
      propertyNames.forEach(propName => obj[propName] = jsonObj[propName]);
    }
    else {
      for (var propName in jsonObj) {
        obj[propName] = jsonObj[propName];
      }
    }
    return obj;
  }

  /**
   * Copy all but specified properties from jsonObj into obj
   * @param obj
   * @param jsonObj
   * @param propertyNamesToExclude
   * @return hydrated object obj
   */
  static copyNonExcludedProperties<T>(obj: T, jsonObj: Object, propertyNamesToExclude: string[]): T {
    for (var propName in jsonObj) {
      if (propertyNamesToExclude.indexOf(propName) < 0) {
        obj[propName] = jsonObj[propName];
      }
    }
    return obj;
  }


}
