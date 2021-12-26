import * as React from "react";
import {Button, Form, Grid, Header, Message, Segment} from 'semantic-ui-react'
import {login} from "../util/api";
import {setUserOnSession} from "../util/sessionUtil";

class LoginPage extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      email: "",
      password: "",
      errorVisible: false
    }
  }

  handleChange = (e) => this.setState({[e.target.name]: e.target.value});

  handleSubmit = (e) => {
    login(this.state.email, this.state.password)
      .then((responseBody) => {
        setUserOnSession(responseBody);
        this.props.redirect("/products");
      }).catch(e => {
      console.log(e);
      this.setState({ errorVisible: true })
      setTimeout(() => {
        this.setState({ errorVisible: false })
      }, 6000)
    });
  };

  handleSignInClick() {
    this.props.redirect("/sign-up");
  }

  render() {
    return (
      <React.Fragment>
        <Grid textAlign='center' style={{height: '100vh'}} verticalAlign='middle'>
          <Grid.Column style={{maxWidth: 450}}>
            <Header as='h2' color='blue' textAlign='center'>
              Login to LegoShop
            </Header>
            <Form size='large'>
              <Segment stacked>
                <Form.Input fluid
                            name="email"
                            placeholder='E-mail address'
                            value={this.state.email}
                            onChange={this.handleChange}
                />
                <Form.Input fluid
                            name="password"
                            placeholder='Password'
                            type='password'
                            value={this.state.password}
                            onChange={this.handleChange}
                />
                <Button color="blue" fluid size='large' onClick={(e) => this.handleSubmit(e)}>
                  Login
                </Button>
              </Segment>
            </Form>
            <Button fluid basic size='large' onClick={(e) => this.handleSignInClick(e)}>
              Sign up
            </Button>
          </Grid.Column>
        </Grid>
          {this.state.errorVisible && <Message negative header='Login failed'/>}
      </React.Fragment>
    );
  }
}

export default LoginPage;