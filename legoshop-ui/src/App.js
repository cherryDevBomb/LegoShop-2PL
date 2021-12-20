import React from "react";
import {Link, Route, Routes, useNavigate} from "react-router-dom";
import {Menu, Segment} from "semantic-ui-react";
import LoginPage from "./components/LoginPage";
import {getLoggedUser, logoutUser} from "./util/sessionUtil";
import SignUp from "./components/SignUp";
import Products from "./components/Products";

export default function App() {
  const navigate = useNavigate();

  const logout = () => {
    logoutUser();
    navigate("/");
  }

  return (
    <React.Fragment>
      {getLoggedUser() && <Segment inverted attached>
        <Menu inverted borderless attached>
          <Menu.Item as={Link} to='/products'>
            View products
          </Menu.Item>
          <Menu.Item as={Link} to='/orders'>
            My orders
          </Menu.Item>
          <Menu.Item position='right' onClick={logout}>
            Log out
          </Menu.Item>
        </Menu>
      </Segment>}

      <Routes>
        <Route exact path={"/"} element={<LoginPage redirect={navigate}/>}/>
        <Route exact path={"/sign-up"} element={<SignUp redirect={navigate}/>}/>
        <Route exact path={"/products"} element={<Products redirect={navigate}/>}/>
      </Routes>
    </React.Fragment>
  );
}
