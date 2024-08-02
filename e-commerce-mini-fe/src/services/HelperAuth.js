import { ROLES } from "../utils/Roles";

export const doLoginLocalStorage = (user,token) => {
    localStorage.setItem("userData", JSON.stringify(user));
    localStorage.setItem("token", JSON.stringify(token));
}

export const getUserFromLocalStorage = () => {
    const data = localStorage.getItem("userData");
    if (data !== null) {
        return JSON.parse(data);
    }
    return null;
}
export const getTokenFromLocalStorage = () => {
    const data = localStorage.getItem("token");
    if (data !== null) {
        return JSON.parse(data);
    }
        return null;

};

export const isLoggedIn = () => {
    if (getTokenFromLocalStorage() !== null) {
        return true;
    } else {
        return false;
    }
};
export const isAdminUser = () => {
    if (isLoggedIn) {
        const roles = getUserFromLocalStorage()?.roles;
        if (roles.find((role) => role.roleName === ROLES.ADMIN)) {
            return true;
        } else {
            return false;
        }
    }
};

export const doLogoutLocalStorage = () => {
    localStorage.removeItem("userData");
    localStorage.removeItem("token");
};