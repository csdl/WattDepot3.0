// Utility functions for get/set/delete cookies
function setCookie(name, value, days) {
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        var expires = "; expires=" + date.toGMTString();
    } else
        var expires = "";
    document.cookie = name + "=" + value + expires + "; path=/";
}

function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ')
            c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0)
            return c.substring(nameEQ.length, c.length);
    }
    return null;
}

function setSelectedTab(tabName) {
    console.log("Setting tab to " + tabName);
    setCookie("selected-tab", tabName);
}

function getKnownUser(id) {
    return USERS[id];
}

function getKnownUserGroup(id) {
    return USERGROUPS[id];
}

function getKnownDepository(id) {
    return DEPOSITORIES[id];
};

function buildDepository(id) {
    var depInfo = getKnownDepository(id);
    var owner = getKnownUserGroup(depInfo['ownerId']);
    var depository = {
        "name" : depInfo['name'],
        "measurementType" : depInfo['measurementType'],
        "owner" : owner
    };
    return depository;
}

function getKnownLocation(id) {
    return LOCATIONS[id];
};

function buildLocation(id) {
    var locInfo = getKnownLocation(id);
    var owner = getKnownUserGroup(locInfo['ownerId']);
    var loc = {
        "id" : locInfo['id'],
        "latitude" : locInfo['latitude'],
        "longitude" : locInfo['longitude'],
        "altitude" : locInfo['altitude'],
        "description" : locInfo['description'],
        "owner" : owner
    };
    return loc;
}

function getKnownSensorModel(id) {
    return MODELS[id];
};

function buildSensorModel(id) {
    var modelInfo = getKnownSensorModel(id);
    var owner = getKnownUserGroup(modelInfo['ownerId']);
    var model = {
        "id" : modelInfo['id'],
        "protocol" : modelInfo['protocol'],
        "type" : modelInfo['type'],
        "version" : modelInfo['version'],
        "owner" : owner
    };
    return model;
};

function getKnownSensor(id) {
    return SENSORS[id];
};

function buildSensor(id) {
    var sensorInfo = getKnownSensor(id);
    var loc = buildLocation(sensorInfo['locationId']);
    var model = buildSensorModel(sensorInfo['modelId']);
    var owner = getKnownUserGroup(sensorInfo['ownerId']);
    var sensor = {
        "id" : sensorInfo['id'],
        "uri" : sensorInfo['uri'],
        "location" : loc,
        "model" : model,
        "owner" : owner
    };
    return sensor;
}

function getKnownSensorGroup(id) {
    return SENSORGROUPS[id];
}

function getKnownSensorProcess(id) {
    return SENSORPROCESSES[id];
}

// ****************** Users **************************
function putNewUser() {
    var id = $("input[name='user_id']").val();
    var first = $("input[name='user_firstname']").val();
    var last = $("input[name='user_lastname']").val();
    var pass = $("input[name='user_password']").val();
    var email = $("input[name='user_email']").val();
    var admin = $("input[name='user_admin']").is(':checked');
    var usr = {
        "id" : id,
        "firstName" : first,
        "lastName" : last,
        "email" : email,
        "password" : pass,
        "admin" : "false",
        "properties" : []
    };
    if (admin) {
        usr['admin'] = "true";
    }
    setSelectedTab('users');
    $.ajax({
        url : '/wattdepot/admin/user/temp',
        type : 'PUT',
        contentType : 'application/json',
        data : JSON.stringify(usr),
        success : function() {
            location.reload();
        },
    });
};

function edit_user_dialog(event, id) {
    var modalElement = $('#addUserModal');
    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });
    var user = getKnownUser(id);
    $("input[name='user_firstname']").val(user['firstName']);
    $("input[name='user_lastname']").val(user['lastName']);
    $("input[name='user_email']").val(user['email']);
    $("input[name='user_id']").val(user['id']);
    $("input[name='user_password']").val(user['password']);
    $("input[name='user_admin']").prop('checked', user['admin']);
    modalElement.modal('show');
};


function delete_user_dialog(event, id) {
    var modalElement = $('#deleteUserModal');

    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });
    modalElement.find('#del_user_id').html(id);
    modalElement.modal('show');
};

function deleteUser() {
    var id = $('#del_user_id').html();
    setSelectedTab('users');
    $.ajax({
        url : '/wattdepot/admin/user/' + id,
        type : 'DELETE',
        contentType : 'application/json',
        success : function() {
            location.reload();
        },
    });
};

// ****************** User Groups **************************
function putNewUserGroup() {
    var id = $("input[name='usergroup_name']").val();
    var selected_ids = $("select[name='groupusers']").val() || [];
    console.log(selected_ids);
    var selected_users = new Array();
    for (var i = 0; i < selected_ids.length; i++) {
        selected_users.push(getKnownUser(selected_ids[i]));
    }
    setSelectedTab('users');
    var grp = {
        "id" : id,
        "users" : selected_users
    };
    $.ajax({
        url : '/wattdepot/admin/usergroup/temp',
        type : 'PUT',
        contentType : 'application/json',
        data : JSON.stringify(grp),
        success : function() {
            location.reload();
        },
    });
};

function edit_usergroup_dialog(event, id) {
    var modalElement = $('#addUserGroupModal');
    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });

    var group = getKnownUserGroup(id);
    $("input[name='usergroup_name']").val(id);
    for (var i = 0; i < group.users.length; i++) {
        var uid = group.users[i].id;
        $('select[name="groupusers"] option[value="' + uid + '"]').prop(
                "selected", "selected");
    }
    modalElement.modal('show');
};

function delete_usergroup_dialog(event, id) {
    var modalElement = $('#deleteUserGroupModal');

    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });
    modalElement.find('#del_usergroup_id').html(id);
    modalElement.modal('show');
};

function deleteUserGroup() {
    var id = $('#del_usergroup_id').html();
    setSelectedTab('users');
    $.ajax({
        url : '/wattdepot/admin/usergroup/' + id,
        type : 'DELETE',
        contentType : 'application/json',
        success : function() {
            location.reload();
        },
    });
};

// ****************** Depositories **************************
function putNewDepository() {
    var id = $("input[name='depository_name']").val();
    var type = $("input[name='depository_type']").val();
    var owner = getKnownUserGroup(GROUPID);
    var depo = {
        "name" : id,
        "measurementType" : type,
        "owner" : owner
    };
    setSelectedTab('depositories');
    $.ajax({
        url : '/wattdepot/' + GROUPID + '/depository/temp',
        type : 'PUT',
        contentType : 'application/json',
        data : JSON.stringify(depo),
        success : function() {
            location.reload();
        },
    });
};

function edit_depository_dialog(event, id) {
    var modalElement = $('#addDepositoryModal');
    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });

    var depo = getKnownDepository(id);
    $("input[name='depository_name']").val(depo['name']);
    $("input[name='depository_type']").val(depo['measurementType']);
    modalElement.modal('show');
};

function delete_depository_dialog(event, id) {
    var modalElement = $('#deleteDepositoryModal');

    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });
    modalElement.find('#del_depository_id').html(id);
    modalElement.modal('show');
};

function deleteDepository() {
    var id = $('#del_depository_id').html();
    setSelectedTab('depositories');
    $.ajax({
        url : '/wattdepot/' + GROUPID + '/depository/' + id,
        type : 'DELETE',
        contentType : 'application/json',
        success : function() {
            location.reload();
        },
    });
};

// ****************** Locations **************************
function putNewLocation() {
    var id = $("input[name='location_id']").val();
    var latitude = $("input[name='location_latitude']").val();
    var longitude = $("input[name='location_longitude']").val();
    var altitude = $("input[name='location_altitude']").val();
    var description = $("input[name='location_description']").val();
    var owner = getKnownUserGroup(GROUPID);
    var loc = {
        "id" : id,
        "latitude" : latitude,
        "longitude" : longitude,
        "altitude" : altitude,
        "description" : description,
        "owner" : owner
    };
    setSelectedTab('locations');
    $.ajax({
        url : '/wattdepot/' + GROUPID + '/location/temp',
        type : 'PUT',
        contentType : 'application/json',
        data : JSON.stringify(loc),
        success : function() {
            location.reload();
        },
    });
};

function putNewInlineLocation() {
    var id = $("input[name='inline_location_id']").val();
    var latitude = $("input[name='inline_location_latitude']").val();
    var longitude = $("input[name='inline_location_longitude']").val();
    var altitude = $("input[name='inline_location_altitude']").val();
    var description = $("input[name='inline_location_description']").val();
    var owner = getKnownUserGroup(GROUPID);
    var loc = {
        "id" : id,
        "latitude" : latitude,
        "longitude" : longitude,
        "altitude" : altitude,
        "description" : description,
        "owner" : owner
    };
    setSelectedTab('locations');
    $.ajax({
        url : '/wattdepot/' + GROUPID + '/location/temp',
        type : 'PUT',
        contentType : 'application/json',
        data : JSON.stringify(loc),
        success : function() {
            location.reload();
        },
    });
};

function edit_location_dialog(event, id) {
    var modalElement = $('#addLocationModal');
    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });

    var loc = getKnownLocation(id);
    $("input[name='location_id']").val(loc['id']);
    $("input[name='location_latitude']").val(loc['latitude']);
    $("input[name='location_longitude']").val(loc['longitude']);
    $("input[name='location_altitude']").val(loc['altitude']);
    $("input[name='location_description']").val(loc['description']);
    modalElement.modal('show');
};

function delete_location_dialog(event, id) {
    var modalElement = $('#deleteLocationModal');

    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });
    modalElement.find('#del_location_id').html(id);
    modalElement.modal('show');
};

function deleteLocation() {
    var id = $('#del_location_id').html();
    setSelectedTab('locations');
    $.ajax({
        url : '/wattdepot/' + GROUPID + '/location/' + id,
        type : 'DELETE',
        contentType : 'application/json',
        success : function() {
            location.reload();
        },
    });
};

// ****************** Sensors **************************
function putNewSensor() {
    var id = $("input[name='sensor_id']").val();
    var uri = $("input[name='sensor_uri']").val();
    var selected_loc = $("select[name='sensor_location']").val();
    var loc = buildLocation(selected_loc);
    var selected_model = $("select[name='sensor_model']").val();
    var model = buildSensorModel(selected_model);
    var owner = getKnownUserGroup(GROUPID);    
    var sensor = {
        "id" : id,
        "uri" : uri,
        "location" : loc,
        "model" : model,
        "owner" : owner
    };
    setSelectedTab('sensors');
    $.ajax({
        url : '/wattdepot/' + GROUPID + '/sensor/temp',
        type : 'PUT',
        contentType : 'application/json',
        data : JSON.stringify(sensor),
        success : function() {
            location.reload();
        },
    });
};

function edit_sensor_dialog(event, id) {
    var modalElement = $('#addSensorModal');
    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });

    var sensor = getKnownSensor(id);
    $("input[name='sensor_id']").val(sensor['id']);
    $("input[name='sensor_uri']").val(sensor['uri']);
    var lid = sensor.location;
    $('select[name="sensor_location"] option[value="' + lid + '"]').prop(
            "selected", "selected");
    var mid = sensor.model;
    $('select[name="sensor_model"] option[value="' + mid + '"]').prop(
            "selected", "selected");

    modalElement.modal('show');
};

function delete_sensor_dialog(event, id) {
    var modalElement = $('#deleteSensorModal');

    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });
    modalElement.find('#del_sensor_id').html(id);
    modalElement.modal('show');
};

function deleteSensor() {
    var id = $('#del_sensor_id').html();
    setSelectedTab('sensors');
    $.ajax({
        url : '/wattdepot/' + GROUPID + '/sensor/' + id,
        type : 'DELETE',
        contentType : 'application/json',
        success : function() {
            location.reload();
        },
    });
};

// ****************** Sensor Groups **************************
function putNewSensorGroup() {
    var id = $("input[name='sensorgroup_name']").val();
    var selected_ids = $("select[name='groupsensors']").val() || [];
    var selected_sensors = new Array();
    for (var i = 0; i < selected_ids.length; i++) {
        selected_sensors.push(buildSensor(selected_ids[i]));
    }
    var owner = getKnownUserGroup(GROUPID);    
    
    setSelectedTab('sensorgroups');
    var grp = {
        "id" : id,
        "sensors" : selected_sensors,
        "owner" : owner
    };
    $.ajax({
        url : '/wattdepot/admin/sensorgroup/temp',
        type : 'PUT',
        contentType : 'application/json',
        data : JSON.stringify(grp),
        success : function() {
            location.reload();
        },
    });
};

function edit_sensorgroup_dialog(event, id) {
    var modalElement = $('#addSensorGroupModal');
    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });

    var group = getKnownSensorGroup(id);
    $("input[name='sensorgroup_name']").val(id);
    for (var i = 0; i < group.sensors.length; i++) {
        var uid = group.sensors[i].id;
        $('select[name="groupsensors"] option[value="' + uid + '"]').prop(
                "selected", "selected");
    }
    modalElement.modal('show');
};

function delete_sensorgroup_dialog(event, id) {
    var modalElement = $('#deleteSensorGroupModal');

    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });
    modalElement.find('#del_sensorgroup_id').html(id);
    modalElement.modal('show');
};

function deleteSensorGroup() {
    var id = $('#del_sensorgroup_id').html();
    setSelectedTab('sensorgroups');
    $.ajax({
        url : '/wattdepot/admin/sensorgroup/' + id,
        type : 'DELETE',
        contentType : 'application/json',
        success : function() {
            location.reload();
        },
    });
};

// ****************** Sensor Models **************************
function putNewModel() {
    var id = $("input[name='model_id']").val();
    var protocol = $("input[name='model_protocol']").val();
    var type = $("input[name='model_type']").val();
    var version = $("input[name='model_version']").val();
    var model = {
        "id" : id,
        "protocol" : protocol,
        "type" : type,
        "version" : version
    };
    setSelectedTab('sensormodels');
    $.ajax({
        url : '/wattdepot/' + GROUPID + '/sensormodel/temp',
        type : 'PUT',
        contentType : 'application/json',
        data : JSON.stringify(model),
        success : function() {
            location.reload();
        },
    });
};

function putNewInlineModel() {
    var id = $("input[name='inline_model_id']").val();
    var protocol = $("input[name='inline_model_protocol']").val();
    var type = $("input[name='inline_model_type']").val();
    var version = $("input[name='inline_model_version']").val();
    var model = {
        "id" : id,
        "protocol" : protocol,
        "type" : type,
        "version" : version
    };
    setSelectedTab('sensormodels');
    $.ajax({
        url : '/wattdepot/' + GROUPID + '/sensormodel/temp',
        type : 'PUT',
        contentType : 'application/json',
        data : JSON.stringify(model),
        success : function() {
            location.reload();
        },
    });
};

function edit_model_dialog(event, id) {
    var modalElement = $('#addModelModal');
    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });

    var model = getKnownSensorModel(id);
    $("input[name='model_id']").val(model['id']);
    $("input[name='model_protocol']").val(model['protocol']);
    $("input[name='model_type']").val(model['type']);
    $("input[name='model_version']").val(model['version']);
    modalElement.modal('show');
};

function delete_model_dialog(event, id) {
    var modalElement = $('#deleteModelModal');

    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });
    modalElement.find('#del_model_id').html(id);
    modalElement.modal('show');
};

function deleteModel() {
    var id = $('#del_model_id').html();
    setSelectedTab('sensormodels');
    $.ajax({
        url : '/wattdepot/' + GROUPID + '/sensormodel/' + id,
        type : 'DELETE',
        contentType : 'application/json',
        success : function() {
            location.reload();
        },
    });
};

//****************** Sensor Processes **************************
function putNewProcess() {
    var id = $("input[name='sensorprocess_name']").val();
    var selected_sensor = $("select[name='process_sensor']").val();
    var sensor = buildSensor(selected_sensor);
    var interval = $("input[name='sensorprocess_polling']").val();
    var selected_depository = $("select[name='process_depository']").val();
    var depo = buildDepository(selected_depository);
    var owner = getKnownUserGroup(GROUPID);    
    var process = {
        "id" : id,
        "sensor" : sensor,
        "pollingInterval" : interval,
        "depositoryId" : selected_depository,
        "owner" : owner
    };
    setSelectedTab('sensorprocesses');
    $.ajax({
        url : '/wattdepot/' + GROUPID + '/sensorprocess/temp',
        type : 'PUT',
        contentType : 'application/json',
        data : JSON.stringify(process),
        success : function() {
            location.reload();
        },
    });
};

function edit_process_dialog(event, id) {
    var modalElement = $('#addProcessModal');
    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });

    var process = getKnownSensorProcess(id);
    $("input[name='sensorprocess_name']").val(process['id']);
    var lid = process.sensorId;
    $('select[name="process_sensor"] option[value="' + lid + '"]').prop(
            "selected", "selected");
    $("input[name='sensorprocess_polling']").val(process['pollingInterval']);
    var mid = process.depositoryId;
    $('select[name="process_depository"] option[value="' + mid + '"]').prop(
            "selected", "selected");
    
    modalElement.modal('show');
};

function delete_process_dialog(event, id) {
    var modalElement = $('#deleteSensorProcessModal');

    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });
    modalElement.find('#del_sensorprocess_id').html(id);
    modalElement.modal('show');
};

function deleteSensorProcess() {
    var id = $('#del_sensorprocess_id').html();
    setSelectedTab('sensorprocesses');
    $.ajax({
        url : '/wattdepot/' + GROUPID + '/sensorprocess/' + id,
        type : 'DELETE',
        contentType : 'application/json',
        success : function() {
            location.reload();
        },
    });
};

