function getHouse(hostName) {
    var houseResult;

    $.ajax({
        dataType: "json",
        url: "house.json",
        async: false,
        success: function (House) {
            $.each(House, function (house, houseInformation) {
                if (houseInformation.host == hostName) {
                    houseResult = houseInformation;

                    return houseResult;
                }
            });
        }
    });

    return houseResult;
}

function getUser(userName) {
    var userResult;

    $.ajax({
        dataType: "json",
        url: "user.json",
        async: false,
        success: function (User) {
            $.each(User, function (user, userInformation) {
                if (userInformation.username == userName) {
                    userResult = userInformation;

                    return userResult;
                }
            });
        }
    });

    return userResult;
}

function initHouse() {

}

function initUser() {

}

function setHouse(house) {

}

function setUser(user) {

}