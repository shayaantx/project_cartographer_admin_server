const USERNAME_FIELD_LOCATION = 'td:nth-child(1)';
const VALIDATION_TOKEN_FIELD_LOCATION = 'td:nth-child(2)';
const EMAIL_FIELD_LOCATION = 'td:nth-child(4)';
const NEW_USER_EMAIL_DIALOG_INPUT_NAME = '#newUserEmail';

let currentNewUserRowToEdit;
let dialog;

function getNewUserUsername() {
  if (currentNewUserRowToEdit === null) {
    throw "New user has not been set";
  }
  return currentNewUserRowToEdit.find(USERNAME_FIELD_LOCATION).children().html();
}

function resendActivationEmail(username) {
  const postData = JSON.stringify({
    username: username
  });
  post(
    "resendNewUserEmail",
    postData,
    function(result) {
      showSuccessToast('Activation sent!');
    }
  );
}

function editNewUserEmail() {
  const email = $(NEW_USER_EMAIL_DIALOG_INPUT_NAME).val();
  const postData = JSON.stringify({
    username: getNewUserUsername(),
    email: email
  });
  post(
    "editNewUserEmail",
    postData,
    function(result) {
      //reset the new user email dialog input
      $(NEW_USER_EMAIL_DIALOG_INPUT_NAME).val("");
      //change the underlying tables email value
      currentNewUserRowToEdit.find(EMAIL_FIELD_LOCATION).children().html(email);
      //reset the current new user row that is selected
      currentNewUserRowToEdit = null;
      dialog.dialog("close");

      showSuccessToast('Successfully edited email');
    }
  );
}

function activateUser(validationToken) {
    window.location.replace(window.location.protocol + "activateUser?validationToken=" + encodeURIComponent(validationToken));
}

function initNewUsers() {
  $(document).ready(function () {
    dialog = $("#editEmailDialog").dialog({
      autoOpen: false,
      height: 160,
      width: 350,
      modal: true,
      buttons: {
        "Save": editNewUserEmail
      }
    });

    $(".activate").click(function() {
        var validationToken = $(this).parent().parent().find(VALIDATION_TOKEN_FIELD_LOCATION).children().html();
        activateUser(validationToken);
    });

    $(".editEmail").click(function() {
        let email = $(this).parent().parent().find(EMAIL_FIELD_LOCATION).children().html();
        $(NEW_USER_EMAIL_DIALOG_INPUT_NAME).val(email);
        currentNewUserRowToEdit = $(this).parent().parent();
        dialog.dialog("open");
    });

    $(".resend").click(function() {
      let username = $(this).parent().parent().find(USERNAME_FIELD_LOCATION).children().html();
      resendActivationEmail(username);
    });
  });
}