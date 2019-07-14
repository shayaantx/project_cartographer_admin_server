function lookupUserById(id) {
    window.location.replace(window.location.protocol + "user?id=" + id);
}

function lookupUserByUsername(username) {
    window.location.replace(window.location.protocol + "user?username=" + encodeURIComponent(username));
}

function banUserById(id, comments) {
    window.location.replace(window.location.protocol + "banUser?id=" + id + "&comments=" + encodeURIComponent(comments));
}

function banMachineById(userId, machineId) {
    window.location.replace(window.location.protocol + "banMachine?userId=" + userId + "&machineId=" + machineId);
}

function unbanMachineById(userId, machineId) {
    window.location.replace(window.location.protocol + "unbanMachine?userId=" + userId + "&machineId=" + machineId);
}

function banAllMachines(userId) {
    window.location.replace(window.location.protocol + "banAllMachines?userId=" + userId);
}

function unbanUserById(id, comments) {
    window.location.replace(window.location.protocol + "unbanUser?id=" + id + "&comments=" + encodeURIComponent(comments));
}

function updateUser(id, username, email, userType, comments) {
    window.location.replace(window.location.protocol + "updateUser?id=" + id + "&username=" + encodeURIComponent(username) + "&email=" + encodeURIComponent(email) + "&userType=" + userType + "&comments=" + encodeURIComponent(comments));
}

function activateUser(validationToken) {
    window.location.replace(window.location.protocol + "activateUser?validationToken=" + encodeURIComponent(validationToken));
}

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

function initCommonJs() {
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
        $("#findButton").click(function() {
            var id = $('#idToFind').val();
            var username = $('#usernameToFind').val();
            var email = $('#emailToFind').val();
            if (!id && !username && !email) {
                alert("Must provide a id or username or email");
            }
            if (id) {
                lookupUserById(id);
                return;
            }
            if (username) {
                lookupUserByUsername(username);
            }
        });

        $("#banUser").click(function() {
            var id = $('#id').val();
            var comments = $('#comments').val();
            banUserById(id, comments);
        });

        $(".banMachine").click(function() {
            var userId = $('#id').val();
            var machineId = $(this).parent().parent().find('td:first').children().html();
            banMachineById(userId, machineId);
        });

        $(".unbanMachine").click(function() {
            var userId = $('#id').val();
            var machineId = $(this).parent().parent().find('td:first').children().html();
            unbanMachineById(userId, machineId);
        });

        $("#banAllMachines").click(function() {
            var userId = $('#id').val();
            banAllMachines(userId);
        });

        $("#unbanUser").click(function() {
            var id = $('#id').val();
            var comments = $('#comments').val();
            unbanUserById(id, comments);
        });

        $("#updateUser").click(function() {
            var id = $('#id').val();
            var username = $('#username').val();
            var email = $('#email').val();
            var userType = $('#accountType').val();
            var comments = $('#comments').val();
            updateUser(id, username, email, userType, comments);
        });

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

function onUserSelect() {
}