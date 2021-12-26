import React from "react";
import {deleteOrder, getAllOrders} from "../util/api";
import {Button, Card, Message} from "semantic-ui-react";
import {getLoggedUserId} from "../util/sessionUtil";

class Orders extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      orders: [],
      messageVisible: false,
      errorVisible: false
    };
  }

  componentDidMount() {
    this.getOrders();
  }

  getOrders() {
    getAllOrders(getLoggedUserId()).then((data) => {
      if (data) {
        this.setState({orders: data});
      }
    });
  }

  handleDeleteClick = (e, order) => {
    deleteOrder(order.id, order.customerId, order.legoSetId)
      .then(() => {
        this.setState({messageVisible: true})
        setTimeout(() => {
          this.setState({messageVisible: false})
        }, 6000);
        this.getOrders();
      }).catch(e => {
      console.log(e);
      this.setState({errorVisible: true})
      setTimeout(() => {
        this.setState({errorVisible: false})
      }, 6000);
    });
  };

  render() {
    return (
      <React.Fragment>
        <Card.Group itemsPerRow={6} className="cards">
          {this.state.orders.map((order) => {
            return (
              <div key={order.id}>
                <Card>
                  <Card.Content>
                    <Card.Header>{order.setName}</Card.Header>
                    <Card.Meta>Code {order.uniqueSetId}</Card.Meta>
                    <Card.Description>
                      <p>Order date: {order.createdDate}</p>
                    </Card.Description>
                  </Card.Content>
                  <Card.Content>
                    <Card.Header>{order.price}$</Card.Header>
                    <Button floated='right' color="red" onClick={(e) => this.handleDeleteClick(e, order)}>
                      Delete order
                    </Button>
                  </Card.Content>
                </Card>
              </div>
            );
          })}
        </Card.Group>
        <div className="bottom-stuff">
          {this.state.messageVisible && <Message positive className="bottom" header='Order deleted'/>}
          {this.state.errorVisible && <Message negative className="bottom" header='Error deleting order'/>}
        </div>
      </React.Fragment>
    )
  }
}

export default Orders;