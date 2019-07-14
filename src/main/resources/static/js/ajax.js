function post(path, data, onSuccess) {
  var csrfToken = $('#_csrf').attr("content");
  var csrfHeader = $('#_csrf_header').attr("content");
  $.ajax({
    url: window.location.protocol + path,
    dataType: "json",
    type: 'POST',
    contentType: 'application/json; charset=utf-8',
    beforeSend: function(xhr){
      xhr.setRequestHeader(csrfHeader, csrfToken);
    },
    data: data,
    error: function(request, status, error) {
      alert(request.responseText);
    },
    success: onSuccess
  });
}