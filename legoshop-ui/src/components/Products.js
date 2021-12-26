import React from "react";
import {addOrder, getAllProducts} from "../util/api";
import {Button, Card, Icon, Message} from "semantic-ui-react";
import {getLoggedUserId} from "../util/sessionUtil";

class Products extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      products: [],
      messageVisible: false,
      errorVisible: false
    };
  }

  componentDidMount() {
    this.getProducts();
  }

  getProducts() {
    getAllProducts().then((data) => {
      if (data) {
        this.setState({products: data});
      }
    });
  }

  handleBuyClick = (e, legosetId) => {
    addOrder(getLoggedUserId(), legosetId)
      .then((responseBody) => {
        this.setState({messageVisible: true})
        setTimeout(() => {
          this.setState({messageVisible: false})
        }, 6000);
        this.getProducts();
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
          {this.state.products.map((legoset) => {
            return (
              <div key={legoset.id}>
                <Card>
                  <Card.Content>
                    <Card.Header>{legoset.setName}</Card.Header>
                    <Card.Meta>Code {legoset.uniqueSetId}</Card.Meta>
                    <Card.Description>
                      <p>Category: {legoset.category}</p>
                      <Icon name='puzzle piece'/> {legoset.piecesCount} pieces
                    </Card.Description>
                  </Card.Content>
                  <Card.Content>
                    <Card.Header>{legoset.price}$</Card.Header>
                    <Button floated='right' color="yellow" onClick={(e) => this.handleBuyClick(e, legoset.id)}>
                      Buy
                    </Button>
                    <Card.Meta>{legoset.availableUnits} units in stock</Card.Meta>
                  </Card.Content>
                </Card>
              </div>
            );
          })}
        </Card.Group>
        <div className="bottom-stuff">
          {this.state.messageVisible && <Message positive className="bottom" header='Order added'/>}
          {this.state.errorVisible && <Message negative className="bottom" header='Error adding order'/>}
        </div>
      </React.Fragment>
    )
  }
}

export default Products;