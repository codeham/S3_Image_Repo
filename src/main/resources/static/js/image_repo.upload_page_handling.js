

    var loadFile = function(event){
        console.log('loadFile() was triggered by change event')
        var output = document.getElementById('output');
        objectURL = URL.createObjectURL(event.target.files[0]);
        console.log(objectURL);
        output.src = objectURL
    }

    var cacheFileUpload = function(event){

    }

    var postImageRequest = function(event){
        let img = event.target.files[0]
        console.log(img)
//        const files = document.getElementById('inputGroupFile01')
//        const file = files[0]
//        console.log(file)
//        const url = "";
//        const formData = new FormData();
//        formData.append('file', file);
//        const config = {
//            headers: {
//                'content-type': 'multipart/form-data'
//            }
//        }
//        axios.post(url, formData, headers)
//        .then(function(){
//            console.log('file upload success !')
//        }).catch(function(){
//            console.log('failure to upload file !')
//        })
    }

    axios.get('/storage/getTest', {
        params: {}
      })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      })
      .then(function () {
        // always executed
      });


