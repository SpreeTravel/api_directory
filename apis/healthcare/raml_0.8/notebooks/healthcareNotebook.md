---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7636/versions/7762/portal/pages/6340/preview
apiNotebookVersion: 1.1.66
title: Healthcare.gov notebook
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```



This method picks a page ID from the given list. The page ID returned determines both english and spanish pages.



```javascript

function getSomeItemId(body){

  

  var lists = ["articles", "blog", "questions", "states", "glossary", "topics"]

  

  for(var fieldName in body){

    if(lists.indexOf(fieldName)>=0){

      

      var value = body[fieldName]

      for(var ind in value){

        var item = value[ind];

        var id = item.id;

        if(id==null){

          id = item.url

        }

        if(id!=null){

          var pref = id.substring(0,4)

          if(pref=="/es/"){

            ind = id.lastIndexOf('/');

            if(ind<0){

              ind = 0

            }

            else{

              ind++;

            }

            var result = id.substring(ind)

            return result;

          }

        }

      }

    }

  }

}

```

```javascript

// Read about the Healthcare_gov API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7636/versions/7762/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7636/versions/7762/definition');

```



Returns content of all pages



```javascript

articlesResponse = client.api.articles.json.get()

```

```javascript

assert.equal(articlesResponse.status, 200, "Error")

```



Pick an article



```javascript

articleId = getSomeItemId(articlesResponse.body)

```



Returns page's content



```javascript

articleResponse = client.pageName(articleId).json.get()

```

```javascript

assert.equal(articleResponse.status, 200, "Error")

assert.equal(articleResponse.body.id, '/'+articleId, "Error")

```



Returns page's content



```javascript

articleESResponse = client.es.pageName(articleId).json.get()

```

```javascript

assert.equal(articleESResponse.status, 200, "Error")

```



Returns content of all question pages



```javascript

questionsResponse = client.api.questions.json.get()

```

```javascript

assert.equal(questionsResponse.status, 200, "Error")

```



Pick some question page



```javascript

questionId = getSomeItemId(questionsResponse.body,true)

```



Returns page's content



```javascript

questionResponse = client.question.pageName(questionId).json.get()

```

```javascript

assert.equal(questionResponse.status, 200, "Error")

assert.equal(questionResponse.body.id, '/question/'+questionId, "Error")

```



Returns page's content



```javascript

questionESResponse = client.es.question.pageName("are-there-any-exceptions-to-these-free-benefits").json.get()

```

```javascript

assert.equal(questionESResponse.status, 200, "Error")

```



Returns content of all blog page



```javascript

blogResponse = client.api.blog.json.get()

```

```javascript

assert.equal(blogResponse.status, 200, "Error")

```

```javascript

blogId = getSomeItemId(blogResponse.body)

```



Returns page's content



```javascript

blogPageResponse = client.blog.pageName(blogId).json.get()

```

```javascript

assert.equal(blogPageResponse.status, 200, "Error")

assert.equal(blogPageResponse.body.id, '/blog/'+blogId, "Error")

```



Returns page's content



```javascript

blogPageESResponse = client.es.blog.pageName(blogId).json.get()

```

```javascript

assert.equal(blogPageESResponse.status, 200, "Error")

```



Returns content of all glossary page



```javascript

glossaryResponse = client.api.glossary.json.get()

```

```javascript

assert.equal(glossaryResponse.status, 200, "Error")

```



Pick some glossary page



```javascript

glossaryId = getSomeItemId(glossaryResponse.body)

```



Returns page's content



```javascript

glossaryPageResponse = client.glossary.pageName(glossaryId).json.get()

```

```javascript

assert.equal(glossaryPageResponse.status, 200, "Error")

assert.equal(glossaryPageResponse.body.id, '/glossary/'+glossaryId, "Error")

```



Returns page's content



```javascript

glossaryPageESResponse = client.es.glossary.pageName(glossaryId).json.get()

```

```javascript

assert.equal(glossaryPageESResponse.status, 200, "Error")

```



Returns content of all states pages



```javascript

statesResponse = client.api.states.json.get()

```

```javascript

assert.equal(statesResponse.status, 200, "Error")

```



Pick some state page



```javascript

stateId = getSomeItemId(statesResponse.body)

```



Returns page's content



```javascript

stateResponse = client.stateName(stateId).json.get()

```

```javascript

assert.equal(stateResponse.status, 200, "Error")

assert.equal(stateResponse.body.id, '/'+stateId, "Error")

```



Returns page's content



```javascript

stateESResponse = client.es.stateName(stateId).json.get()

```

```javascript

assert.equal(stateESResponse.status, 200, "Error")

```



Returns content of all topic pages



```javascript

topicsResponse = client.api.topics.json.get()

```

```javascript

assert.equal(topicsResponse.status, 200, "Error")

```



Pick some topic page



```javascript

topicId = getSomeItemId(topicsResponse.body)

```



Returns page's content



```javascript

topicResponse = client.pageName(topicId).json.get()

```

```javascript

assert.equal(topicResponse.status, 200, "Error")

assert.equal(topicResponse.body.id, '/'+topicId, "Error")

```



Returns page's content



```javascript

topicESResponse = client.es.pageName(topicId).json.get()

```

```javascript

assert.equal(topicESResponse.status, 200, "Error")

```