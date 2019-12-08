const BUTTON_CELL_POSITION = 8;
const MACHINE_BANNED_TEXT_CELL_POSITION = 6;

function getButton(banned) {
  return "<button class=\"" + (!banned ? "banMachine" : "unbanMachine") + "\" type=\"button\" th:case=\"" + (!banned) + "\">" + (!banned ? "Ban" : "Unban") + "</button>";
}

function banMachineById(machineId, machineRow) {
  const postData = JSON.stringify({
    machineId: machineId
  });
  post(
    "banMachine",
    postData,
    function (result) {
      machineRow.children().get(MACHINE_BANNED_TEXT_CELL_POSITION).innerHTML = "true";
      updateBanUnbanMachineButtons(machineRow, true);
      showSuccessToast('Machine ' + machineId + ' banned!');
    });
}

function unbanMachineById(machineId, machineRow) {
  const postData = JSON.stringify({
    machineId: machineId
  });
  post(
    "unbanMachine",
    postData,
    function (result) {
      machineRow.children().get(MACHINE_BANNED_TEXT_CELL_POSITION).innerHTML = "false";
      updateBanUnbanMachineButtons(machineRow, false);
      showSuccessToast('Machine ' + machineId + ' unbanned!');
    });
}

function banAllMachines(userId) {
  const postData = JSON.stringify({
    userId: userId
  });
  post(
    "banAllMachines",
    postData,
    function (result) {
      //TODO: update ui
      $("#machineTable").children().find("tr").each(function(index, tr) {
        if (tr.children[MACHINE_BANNED_TEXT_CELL_POSITION].innerHTML !== "machineBanned") {
          tr.children[MACHINE_BANNED_TEXT_CELL_POSITION].innerHTML = "true"
          tr.children[BUTTON_CELL_POSITION].innerHTML = getButton(true);
          //since we replace the button, re-register the events for these buttons
          registerBanUnbanMachineEvents();
        }
      });
      showSuccessToast('All machines banned!');
    });
}

function updateBanUnbanMachineButtons(machineRow, banned) {
  machineRow.children().get(BUTTON_CELL_POSITION).innerHTML = getButton(banned);
  //since we replace the button, re-register the events for these buttons
  registerBanUnbanMachineEvents();
}

function registerBanUnbanMachineEvents() {
  $(".banMachine").click(function () {
    var machineId = $(this).parent().parent().find('td:first').children().html();
    var machineRow = $(this).parent().parent();
    banMachineById(machineId, machineRow);
  });

  $(".unbanMachine").click(function () {
    var machineId = $(this).parent().parent().find('td:first').children().html();
    var machineRow = $(this).parent().parent();
    unbanMachineById(machineId, machineRow);
  });
  $("#banAllMachines").click(function () {
    var userId = $('#id').val();
    banAllMachines(userId);
  });
}

function initMachine() {
  $(document).ready(function () {
    registerBanUnbanMachineEvents();
  });
}