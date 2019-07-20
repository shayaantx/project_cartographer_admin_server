function searchForUser() {
  const postData = JSON.stringify({
    filterText: $('#filterText').val()
  });
  post(
    "searchForUser",
    postData,
    function(result) {
      var users = result["users"];
      //clear all rows except header
      $("#userTable tr:gt(0)").remove();

      if (users) {
        var html = '';
        for (var i = 0; i < users.length; i++) {
          var user = users[i];

          html += '<tr>';
          var values = user["values"];
          for (var k = 0; k < values.length; k++) {
              html += '<td>' + values[k] + '</td>';
          }
          html += '</tr>';
        }
      }
      $('#userTable tr').first().after(html);
    }
  );
}

function initSearchUsers() {
  //for dynamic data
  $(document).on("click", "#userTable tr", function(){
    var id = $(this).find('td:first').html();
    if (id != "Id") {
      //ignore header row
      lookupUserById(id);
    }
  });

  //for static data
  $(document).ready(function () {
    $("#filterText").on('keyup', function (e) {
      if (e.keyCode == 13) {
        searchForUser();
      }
    });

    $("#searchForUser").click(function() {
      searchForUser();
    });
  });
}