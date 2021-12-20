import * as React from "react";
import {Button, Form, Grid, Header, Segment} from 'semantic-ui-react'
import {login} from "../util/api";
import {setUserOnSession} from "../util/sessionUtil";

class SignUp extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      email: "",
      password: "",
    }
  }

  handleChange = (e) => this.setState({[e.target.name]: e.target.value});

  handleSubmit = (e) => {
    login(this.state.email, this.state.password)
      .then((responseBody) => {
        setUserOnSession(responseBody);
        this.props.history.push("/products");
      }).catch(function (e) {
      console.log(e);
    });
  };

  // handleClick(albumId) {
  //   this.setState({
  //     albumId: albumId
  //   });
  // }

  render() {
    return (
      <React.Fragment>
        <Grid textAlign='center' style={{height: '100vh'}} verticalAlign='middle'>
          <Grid.Column style={{maxWidth: 450}}>
            <Header as='h2' color='blue' textAlign='center'>
              Sign up
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
          </Grid.Column>
        </Grid>
      </React.Fragment>
    );
  }
}

export default SignUp;