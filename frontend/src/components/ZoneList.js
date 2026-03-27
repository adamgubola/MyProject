import React from "react";
import { useHikSystem } from "../hooks/useHikSystem";

const ZoneList = () => {
    const { zones, loading, error, filter, setFilter, executeCommand } = useHikSystem();

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
return (
    <div className="container mt-4">
        <div className="card shadow-sm">
            <div className="card-header bg-dark text-white d-flex justify-content-between align-items-center">
                <h3 className="mb-0">Alarm zones< span className="badge bg-secondary  ms-3">{zones.length}</span></h3>
            </div>
            <div className="card-body">
                 {/* Filter buttons */}
                <div className="btn-group mb-3 w-100" role="group">
                    <button type="button" 
                        className={`btn ${filter === 'all' ? 'btn-primary' : 'btn-outline-primary'}`}
                        onClick={() => setFilter('all')}>
                        All
                    </button>
                    <button type="button"
                        className={`btn ${filter === 'armed' ? 'btn-primary' : 'btn-outline-primary'}`}
                        onClick={() => setFilter('armed')}>
                        Armed
                    </button>
                    <button type="button"
                        className={`btn ${filter === 'disarmed' ? 'btn-primary' : 'btn-outline-primary'}`}
                        onClick={() => setFilter('disarmed')}>
                        Disarmed
                    </button>
                    <button type="button"
                        className={`btn ${filter === 'bypassed' ? 'btn-primary' : 'btn-outline-primary'}`}
                        onClick={() => setFilter('bypassed')}>
                        Bypassed
                    </button>
                    <button type="button"
                        className={`btn ${filter === 'alarming' ? 'btn-primary' : 'btn-outline-primary'}`}
                        onClick={() => setFilter('alarming')}>
                        Alarming
                    </button>
                </div>
                {/* Zone list */}
                <ul className="list-group">
                    {zones.map((zone) => (
                        <li key={zone.id} className={getZoneClass(zone)}>
                            <div>
                                <h5 className="mb-1">{zone.name} <span className="badge bg-secondary ms-2">{zone.id}</span></h5>
                                <div>
                                    {zone.armed ? (
                                        <span className="badge bg-primary me-1">Armed</span>
                                    ) : (
                                        <span className="badge bg-secondary me-1">Disarmed</span>
                                    )}
                                    {zone.bypassed && <span className="badge bg-warning text-dark me-1">Bypassed</span>}
                                    {zone.alarming && <span className="badge bg-danger animate__animated animate__flash me-1">Alarming</span>}
                                </div>
                            </div>
                            <div className="btn-group">
                                {!zone.armed ? (
                                    <button className="btn btn-sm btn-success" onClick={() => executeCommand('arm', zone.id)}>Arm</button>
                                ) : (
                                    <button className="btn btn-sm btn-danger" onClick={() => executeCommand('disarm', zone.id)}>Disarm</button>
                                )}
                                <button className="btn btn-sm btn-warning text-dark" onClick={() => executeCommand(zone.bypassed ? 'unbypass' : 'bypass', zone.id)}>
                                    {zone.bypassed ? 'Unbypass' : 'Bypass'}
                                </button>   
                            </div>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    </div>
);
};
export default ZoneList;