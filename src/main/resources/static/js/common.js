
function getData() {
    $.ajax({
        type: "GET",
        url: "/getAllUsers",
        dataType: "json",
        success: function(response) {
            var i = 0;
            var table = "<table>";
            table += "<tr><td>UserId</td><td>Username</td><td>Email</td></tr>";
            for (i = 0; i < response.length; i++) {
                //{"userId":1,"username":"Glitchy Scripts","email":"glitchyscripts@hotmail.com"}
                var user = response[i];
                table += "<tr>";
                table += "<td>" + user["userId"] + "</td>";
                table += "<td>" + user["username"] + "</td>";
                table += "<td>" + user["email"] + "</td>";
                table += "</tr>";
            }
            table += "</table>";
            $('#allUsers').append(table);
        }
    });

    return false;
}

function lookupUserById(id) {
    window.location.replace(window.location.protocol + "user?id=" + id);
}

function lookupUserByUsername(username) {
    window.location.replace(window.location.protocol + "user?username=" + username);
}

function initCommonJs() {

    $(document).ready(function () {
        $("#userTable tr").click(function(){
            var id = $(this).find('td:first').html();
            lookupUserById(id);
        });
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
    });
}

function onUserSelect() {
}