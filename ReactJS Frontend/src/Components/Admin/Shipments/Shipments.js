import React, { useState, useEffect } from 'react';
import './Shipments.css'; // Importing the CSS file for styling
import instance from '../../../axios'; // Importing the Axios instance for making API requests

const Shipments = () => {
    // State to hold the list of shipments
    const [shipments, setShipments] = useState([]);

    // useEffect to fetch shipments when the component mounts
    useEffect(() => {
        // Function to fetch shipments from the API
        const fetchShipments = async () => {
            try {
                // Sending a GET request to fetch shipments
                const response = await instance.get('http://localhost:8084/api/v1/shipments');
                setShipments(response.data); // Updating state with fetched shipments
            } catch (error) {
                console.error('Error fetching shipments:', error); // Logging any errors
            }
        };

        fetchShipments(); // Call the function to fetch shipments
    }, []); // Empty dependency array means this effect runs once on mount

    return (
        <div className="shipments-container">
            <h1 className='shipments-heading'>Shipments</h1> {/* Heading for the shipments section */}
            <table className="shipments-table">
                <thead>
                    <tr>
                        <th>Shipment ID</th> {/* Column for Shipment ID */}
                        <th>Store ID</th> {/* Column for Store ID */}
                        <th>Customer ID</th> {/* Column for Customer ID */}
                        <th>Delivery Address</th> {/* Column for Delivery Address */}
                        <th>Shipment Status</th> {/* Column for Shipment Status */}
                    </tr>
                </thead>
                <tbody>
                    {/* Mapping over shipments array to display each shipment in a table row */}
                    {shipments.map(shipment => (
                        <tr key={shipment.shipmentId}> {/* Unique key for each row */}
                            <td>{shipment.shipmentId}</td> {/* Shipment ID */}
                            <td>{shipment.storeId}</td> {/* Store ID */}
                            <td>{shipment.customerId}</td> {/* Customer ID */}
                            <td>{shipment.deliveryAddress}</td> {/* Delivery Address */}
                            {/* Conditionally styled Shipment Status cell */}
                            <td className={`status ${shipment.shipmentStatus.toLowerCase()}`}>
                                {shipment.shipmentStatus} {/* Displaying Shipment Status */}
                            </td>                      
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default Shipments; // Exporting the Shipments component
