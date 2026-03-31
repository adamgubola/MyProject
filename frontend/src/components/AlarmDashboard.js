import React, { useState } from "react";
import { useHikSystem } from "../hooks/useHikSystem";

const AlarmDashboard = () => {
    const { zones, partitions, loading, error, filter, setFilter, executeZoneCommand, executePartitionCommand } = useHikSystem();

    const [isAdmin, setIsAdmin] = useState(true);

    const getZoneClass = (zone) => {
        if(zone.alarming) return "list-group-item list-group-item-danger d-flex justify-content-between align-items-center";
        if(zone.bypassed) return "list-group-item list-group-item-warning d-flex justify-content-between align-items-center";
        if(zone.armed) return "list-group-item list-group-item-primary d-flex justify-content-between align-items-center";
        return "list-group-item list-group-item-light d-flex justify-content-between align-items-center";
    };
    if(loading && zones.length === 0) {
        return (
            <div className="text-center mt-5">
                <div className="spinner-border text-primary" role="status">
                    <span className="visually-hidden">Loading...</span>
                </div>
            </div>
        );
    }
    if(error) {
        return (
            <div className="alert alert-danger mt-4" role="alert">
                {error}
            </div>
        );
    }
    const unassignedZones = zones.filter(z =>
        !z.partitionId || !partitions.find(p => p.id === z.partitionId)
    );

return (
    <div className="container mt-4">
           <div className="d-flex justify-content-between align-items-center mb-4">
                <div className="btn-group shadow-sm" role="group">
                    <button type="button" className={`btn ${filter=== 'all' ? 'btn-primary' : 'btn-outline-primary'}`} onClick={() => setFilter('all')}>All</button>
                    <button type="button" className={`btn ${filter=== 'armed' ? 'btn-primary' : 'btn-outline-primary'}`} onClick={() => setFilter('armed')}>Armed</button>
                    <button type="button" className={`btn ${filter=== 'disarmed' ? 'btn-primary' : 'btn-outline-primary'}`} onClick={() => setFilter('disarmed')}>Disarmed</button>
                    <button type="button" className={`btn ${filter=== 'bypassed' ? 'btn-primary' : 'btn-outline-primary'}`} onClick={() => setFilter('bypassed')}>Bypassed</button>
                    <button type="button" className={`btn ${filter=== 'alarming' ? 'btn-primary' : 'btn-outline-primary'}`} onClick={() => setFilter('alarming')}>Alarming</button>
                </div>
                <button className={`btn ${isAdmin ? 'btn-warning' : 'btn-outline-secondary'} fw-bold-shadow`} onClick={()=> setIsAdmin(!isAdmin)}>
                    {isAdmin ? 'Admin function: On' : 'Admin function: Off'}
                </button>
           </div>

           {partitions.map((partition) => {

            const partitionZones = zones.filter(z=> z.partitionId === partition.id);
            const isPartitionAlarming = partitionZones.some(z => z.alarming);

                return(
                    <div key={partition.id} className="card shadow-sm mb-4 border-secondary">
                        <div className="card-header bg-dark text-white d-flex justify-content-between align-items-center">
                            <h4 className="mb-1">{partition.name}
                                <span className=" badge bg-secondary ms-2 text-sm">
                                    ID: {partition.id}
                                </span>
                            </h4>
                            <div className="btn-group">
                                {!partition.armed ? (
                                    <button className="btn btn-success fw-bold" onClick={() => executePartitionCommand('armPartition', partition.id)}>Arm</button>
                                ) : (
                                    <button className="btn btn-danger fw-bold" onClick={() => executePartitionCommand('disarmPartition', partition.id)}>Disarm</button>
                                )}

                                {isPartitionAlarming && (
                                    <button className="btn btn-warning fw-bold ms-2">Acknowledge</button>
                                )}
                                
                                {/* Későbbi funkciók */}
                                {partition.alarming && (
                                    <button className=" btn btn-warning fw-bold ms-2">Acknowledge</button>
                                )}
                                {isAdmin && (
                                    <>
                                    <button className="btn btn-outline-light ms-3" title="Partíció szerkesztése (Hamarosan)">Change</button>
                                    <button className="btn btn-outline-light ms-3" title="Partíció törlése (Hamarosan)">Delet</button>
                                    </>
                                    
                                )}
                            </div>
                        </div>  

                    <div className="card-body p-0">
                            {partitionZones.length > 0 ? (
                                <ul className="list-group list-group-flush">
                                    {partitionZones.map((zone) => (
                                        <li key={zone.id} className={getZoneClass(zone)}>
                                            <div>
                                                <h6 className="mb-1 fw-bold">{zone.name} <span className="text-muted small ms-1">(ID: {zone.id})</span></h6>
                                                <div>
                                                    {zone.armed ? <span className="badge bg-primary me-1">Arm</span> : <span className="badge bg-secondary me-1">Disarm</span>}
                                                    {zone.bypassed && <span className="badge bg-warning text-dark me-1">Disarmed</span>}
                                                    {zone.alarming && <span className="badge bg-danger animate__animated animate__flash me-1">Arming!</span>}
                                                </div>
                                            </div>
                                            <div className="btn-group">
                                                <button className="btn btn-sm btn-outline-dark" onClick={() => executeZoneCommand(zone.bypassed ? 'unbypass' : 'bypass', zone.id)}>
                                                    {zone.bypassed ? 'Unbypass' : 'Bypass'}
                                                </button>
                                                
                                                {/* Admin Funkciók a Zónához */}
                                                {isAdmin && (
                                                    <>
                                                        <button className="btn btn-sm btn-danger ms-2" onClick={() => executeZoneCommand('trigger', zone.id)}>Test alarm</button>
                                                        <button className="btn btn-sm btn-secondary ms-1" title="Zóna törlése (Hamarosan)">Delete</button>
                                                    </>
                                                )}
                                            </div>
                                        </li>
                                    ))}
                                </ul>
                            ) : (
                                <div className="p-3 text-muted fst-italic">No zones for this partition.</div>
                            )}
                        </div>
                    </div>
                );
            })}

            {/* Kiosztatlan zónák */}
            {unassignedZones.length > 0 && (
                <div className="card shadow-sm mb-4 border-warning">
                    <div className="card-header bg-warning text-dark fw-bold d-flex justify-content-between align-items-center">
                        <h5 className="mb-0">Unassigned Zones</h5>
                        {isAdmin && <button className="btn btn-sm btn-dark">Add new zone</button>}
                    </div>
                    <div className="card-body p-0">
                        <ul className="list-group list-group-flush">
                            {unassignedZones.map((zone) => (
                                <li key={zone.id} className={getZoneClass(zone)}>
                                    <div>
                                        <h6 className="mb-1">{zone.name} <span className="text-muted small ms-1">(ID: {zone.id})</span></h6>
                                    </div>
                                    <div className="btn-group">
                                        {isAdmin && (
                                            <button className="btn btn-sm btn-primary" title="Hozzárendelés partícióhoz (Hamarosan)">Move</button>
                                        )}
                                    </div>
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
            )}

        </div>
    );
};

export default AlarmDashboard;