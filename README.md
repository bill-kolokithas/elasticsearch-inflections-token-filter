# Inflections token filter for ElasticSearch

The reasoning behind creating this plugin was for Elastic to be able to do some transformations on a multi-field since we can't alter it directly ourselves.  
This is the only way to apply inflections like singularize and camelize in ElasticSearch multi-fields.

Example:

```
index:
  analysis:
    filter:
      singularize:
        type: inflections,
        inflection: singularize
```


```
"title": {
    "type": "string",
    "fields": {
        "raw":   { "type": "string", "analyzer": "singularize" }
    }
}
```


Inflections are taken from [javalite](http://javalite.io/) which in turn are ported from rails ActiveSupport::Inflector.

## Supported inflections:
- pluralize
- singularize
- capitalize
- tableize - Converts a camel case to underscore and then pluralizes: "GrayDuck" is converted to "gray_ducks"
- underscore - Converts a CamelCase string to underscores: "AliceInWonderLand" becomes: "alice_in_wonderland"
- camelize - Generates a camel case version of a phrase from underscore
