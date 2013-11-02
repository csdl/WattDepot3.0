// Utility functions for get/set/delete cookies
function setCookie(name,value,days) {
    if (days) {
        var date = new Date();
        date.setTime(date.getTime()+(days*24*60*60*1000));
        var expires = "; expires="+date.toGMTString();
    }
    else var expires = "";
    document.cookie = name+"="+value+expires+"; path=/";
}

function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}

function setSelectedTab(tabName) {
    console.log("Setting tab to " + tabName);
    setCookie("selected-tab", tabName);
}

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
    var modalElement = $('#editUserModal');
    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });
    var user = getKnownUser(id);
    $("input[name='edit_user_firstname']").val(user['firstName']);
    $("input[name='edit_user_lastname']").val(user['lastName']);
    $("input[name='edit_user_email']").val(user['email']);
    $("input[name='edit_user_id']").val(user['id']);
    $("input[name='edit_user_password']").val(user['password']);
    $("input[name='edit_user_admin']").prop('checked', user['admin']);        
    modalElement.modal('show');
};

function editUser() {
    var id = $("input[name='edit_user_id']").val();
    var first = $("input[name='edit_user_firstname']").val();
    var last = $("input[name='edit_user_lastname']").val();
    var pass = $("input[name='edit_user_password']").val();
    var email = $("input[name='edit_user_email']").val();
    var admin = $("input[name='edit_user_admin']").is(':checked');
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

function getKnownUser(id) {
    return USERS[id];
}

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

function deleteUseGroup() {
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

function putNewDepository() {
    var id = $("input[name='depository_name']").val();
    var type = $("input[name='depository_type']").val();
    var depo = {
        "name" : id,
        "measurementType" : type
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

function getKnownDepository(id) {
    return DEPOSITORIES[id];
};

function edit_depository_dialog(event, id) {
    var modalElement = $('#editDepositoryModal');
    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });

    var depo = getKnownDepository(id);
    $("input[name='edit_depository_name']").val(depot['name']);
    $("input[name='edit_depository_type']").val(depot['type']);
    modalElement.modal('show');
};

function editDepository() {
    var id = $("input[name='edit_depository_name']").val();
    var type = $("input[name='edit_depository_type']").val();
    var depo = {
        "name" : id,
        "measurementType" : type
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

function delete_depository_dialog(event, id) {
    var modalElement = $('#deleteUserModal');

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
        url : '/wattdepot/' + GROUP + '/depository/' + id,
        type : 'DELETE',
        contentType : 'application/json',
        success : function() {
            location.reload();
        },
    });
};
