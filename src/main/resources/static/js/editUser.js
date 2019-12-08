function updateBanUnbanUserButtons(banned) {
  $(banned ? "#banUser" : "#unbanUser").replaceWith("<button id=\"" + (!banned ? "banUser" : "unbanUser") + "\" type=\"button\" th:case=\"" + (!banned) + "\">" + (!banned ? "Ban" : "Unban") + "</button>");
  //since we replace the button, re-register the events for these buttons
  registerBanUnbanUserEvents();
}

function updateUserFields(user, message) {
  $("#id").val(user["id"]);
  $("#username").val(user["username"]);
  $("#email").val(user["email"]);
  $("#accountType").val(user["userType"]).change();
  $("#comments").val(user["comments"]);
  showSuccessToast(message);
}

function banAndUnBanUser(id, comments, path, successMessage) {
  const postData = JSON.stringify({
    id: id,
    comments: comments
  });
  post(
    path,
    postData,
    function (result) {
      let banned = result["banned"];
      $("#banned").val(result["banned"]);

      updateBanUnbanUserButtons(banned);
      updateUserFields(result, successMessage);
    }
  );
}

function banUserById(id, comments) {
  banAndUnBanUser(id, comments, "banUser", "Banned!");
}

function unbanUserById(id, comments) {
  banAndUnBanUser(id, comments, "unbanUser", "Unbanned!");
}

function updateUser(id, username, email, userType, comments) {
  const postData = JSON.stringify({
    id: id,
    username: username,
    email: email,
    userType: userType,
    comments: comments
  });
  post(
    "updateUser",
    postData,
    function (result) {
      updateUserFields(result, 'Updated user!');
    }
  );
}

function registerBanUnbanUserEvents() {
  $("#banUser").click(function () {
    var id = $('#id').val();
    var comments = $('#comments').val();
    banUserById(id, comments);
  });

  $("#unbanUser").click(function () {
    var id = $('#id').val();
    var comments = $('#comments').val();
    unbanUserById(id, comments);
  });
}

function initEditUsers() {
  $(document).ready(function () {
    $("#findButton").click(function () {
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

    registerBanUnbanUserEvents();

    $("#updateUser").click(function () {
      var id = $('#id').val();
      var username = $('#username').val();
      var email = $('#email').val();
      var userType = $('#accountType').val();
      var comments = $('#comments').val();
      updateUser(id, username, email, userType, comments);
    });
  });
}