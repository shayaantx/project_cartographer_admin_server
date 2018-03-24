function lookupUserById(id) {
    window.location.replace(window.location.protocol + "user?id=" + id);
}

function lookupUserByUsername(username) {
    window.location.replace(window.location.protocol + "user?username=" + username);
}

function banUserById(id) {
    window.location.replace(window.location.protocol + "banUser?id=" + id);
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

function unbanUserById(id) {
    window.location.replace(window.location.protocol + "unbanUser?id=" + id);
}

function updateUser(id, username, email, userType, comments) {
    window.location.replace(window.location.protocol + "updateUser?id=" + id + "&username=" + username + "&email=" + email + "&userType=" + userType + "&comments=" + comments);
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
            banUserById(id);
        });

        $("#banMachine").click(function() {
            var userId = $('#id').val();
            var machineId = $(this).parent().parent().find('td:first').children().html();
            banMachineById(userId, machineId);
        });

        $("#unbanMachine").click(function() {
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
            unbanUserById(id);
        });

        $("#updateUser").click(function() {
            var id = $('#id').val();
            var username = $('#username').val();
            var email = $('#email').val();
            var userType = $('#accountType').val();
            var comments = $('#comments').val();
            updateUser(id, username, email, userType, comments);
        });

        $("#searchForUser").click(function() {
            var filterText = $('#filterText').val();
            var csrfToken = $('#_csrf').attr("content");
            var csrfHeader = $('#_csrf_header').attr("content");
            $.ajax({
                url: window.location.protocol + "searchForUser",
                dataType: "json",
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                beforeSend: function(xhr){
                        xhr.setRequestHeader(csrfHeader, csrfToken);
                },
                data: JSON.stringify({
                    filterText: filterText
                }),
                error: function (request, status, error) {
                    alert(request.responseText);
                },
                success: function(result){
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
            });
        });
    });
}

function onUserSelect() {
}