package nuclibook.routes;

import javafx.util.Pair;
import nuclibook.constants.P;
import nuclibook.entity_utils.*;
import nuclibook.models.*;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;

import java.util.Map;

public class CrudCreateUpdateRoute extends DefaultRoute {

    private boolean createNew = false;

    @Override
    public Object handle(Request request, Response response) throws Exception {
        prepareToHandle();

        // get request info
        String entityType = request.queryParams("entity-type");
        int entityId = 0;
        try {
            entityId = Integer.parseInt(request.queryParams("entity-id"));
        } catch (NumberFormatException e) {
            // leave it at zero
        }
        createNew = entityId == 0;

        // entity to work on
        Pair<Status, Object> entityPair = null;
        Class dbClass = null;

        // build new/updated entity
        switch (entityType) {
            case "camera":
                entityPair = createUpdateCamera(entityId, request);
                dbClass = Camera.class;
                break;

            case "camera-type":
                entityPair = createUpdateCameraType(entityId, request);
                dbClass = CameraType.class;
                break;

            case "tracer":
                entityPair = createUpdateTracer(entityId, request);
                dbClass = Tracer.class;
                break;

            case "patient":
                entityPair = createUpdatePatient(entityId, request);
                dbClass = Patient.class;
                break;

            case "staff":
                entityPair = createUpdateStaff(entityId, request);
                dbClass = Staff.class;
                break;

            case "staff-absence":
                entityPair = createUpdateStaffAbsence(entityId, request);
                dbClass = StaffAbsence.class;
                break;

            case "staff-availability":
                entityPair = createUpdateStaffAvailability(entityId, request);
                dbClass = StaffAvailability.class;
                break;

            case "staff-role":
                entityPair = createUpdateStaffRole(entityId, request);
                dbClass = StaffRole.class;
                break;

            case "therapy":
                entityPair = createUpdateTherapy(entityId, request);
                dbClass = Therapy.class;
                break;
        }

        // checks if entity was created
        if (entityPair == null) {
            return "error";
        }

        // save/update
        if (entityPair.getKey() == Status.OK) {
            if (entityPair.getValue() != null) {
                if (createNew) {
                    AbstractEntityUtils.createEntity(dbClass, entityPair.getValue());
                } else {
                    AbstractEntityUtils.updateEntity(dbClass, entityPair.getValue());
                }
            }

            // anything to do afterwards?
            if (entityType.equals("staff-role") || entityType.equals("staff")) {
                SecurityUtils.getCurrentUser().refreshPermissions();
            }

            return "okay";
        }

        // failed validation
        if (entityPair.getKey() == Status.FAILED_VALIDATION) {
            return "failed_validation";
        }

        // no permission
        if (entityPair.getKey() == Status.NO_PERMISSION) {
            return "no_permission";
        }

        // fail safe
        return "error";
    }

    private Pair<Status, Object> createUpdateCamera(int entityId, Request request) {
        // permission
        if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_CAMERAS)) {
            return new Pair<>(Status.NO_PERMISSION, null);
        }

        // validation
        if (request.queryParams("room-number").length() > 32
                || !request.queryParams("room-number").matches("[a-zA-Z0-9\\-\\. ]+")) {
            return new Pair<>(Status.FAILED_VALIDATION, null);
        }

        // create and set ID
        Camera entity;
        if (createNew) {
            entity = new Camera();
        } else {
            entity = AbstractEntityUtils.getEntityById(Camera.class, entityId);
        }

        // room-number
        entity.setRoomNumber(request.queryParams("room-number"));

        // type
        CameraType type = CameraTypeUtils.getCameraType(request.queryParams("camera-type-id"));
        entity.setType(type);

        return new Pair<>(Status.OK, entity);
    }

    private Pair<Status, Object> createUpdateCameraType(int entityId, Request request) {
        // permission
        if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_CAMERAS)) {
            return new Pair<>(Status.NO_PERMISSION, null);
        }

        // validation
        if (request.queryParams("label").length() > 64
                || !request.queryParams("label").matches("[a-zA-Z\\-\\. ]+")) {
            return new Pair<>(Status.FAILED_VALIDATION, null);
        }

        // create and set ID
        CameraType entity;
        if (createNew) {
            entity = new CameraType();
        } else {
            entity = AbstractEntityUtils.getEntityById(CameraType.class, entityId);
        }

        // label
        entity.setLabel(request.queryParams("label"));

        return new Pair<>(Status.OK, entity);
    }

    private Pair<Status, Object> createUpdatePatient(int entityId, Request request) {
        // permission
        if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_PATIENTS)) {
            return new Pair<>(Status.NO_PERMISSION, null);
        }

        // validation
        if (request.queryParams("name").length() > 64
                || request.queryParams("hospital-number").length() > 64
                || !request.queryParams("name").matches("[a-zA-Z\\-\\.' ]+")
                || !request.queryParams("hospital-number").matches("[a-zA-Z0-9\\-]+")
                || !request.queryParams("year-of-birth").matches("[0-9]{4}")
                || !request.queryParams("month-of-birth").matches("[0-9]{2}")
                || !request.queryParams("day-of-birth").matches("[0-9]{1,2}")) {
            return new Pair<>(Status.FAILED_VALIDATION, null);
        }

        // create and set ID
        Patient entity;
        if (createNew) {
            entity = new Patient();
        } else {
            entity = AbstractEntityUtils.getEntityById(Patient.class, entityId);
        }

        // name
        entity.setName(request.queryParams("name"));

        // hospital number
        entity.setHospitalNumber(request.queryParams("hospital-number"));

        // dob
        entity.setDateOfBirth(new DateTime(request.queryParams("year-of-birth") + "-" + request.queryParams("month-of-birth") + "-" + request.queryParams("day-of-birth")));

        return new Pair<>(Status.OK, entity);
    }

    private Pair<Status, Object> createUpdateStaff(int entityId, Request request) {
        // permission
        if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_STAFF)) {
            return new Pair<>(Status.NO_PERMISSION, null);
        }

        // validation
        if (request.queryParams("name").length() > 64
                || request.queryParams("username").length() > 64
                || !request.queryParams("name").matches("[a-zA-Z\\-\\.' ]+")
                || !request.queryParams("username").matches("[a-zA-Z0-9]+")) {
            return new Pair<>(Status.FAILED_VALIDATION, null);
        }

        // create and set ID
        Staff entity;
        if (createNew) {
            entity = new Staff();
        } else {
            entity = AbstractEntityUtils.getEntityById(Staff.class, entityId);
        }

        // names
        entity.setName(request.queryParams("name"));
        entity.setUsername(request.queryParams("username"));

        // password
        if (request.queryParams("password") != null && request.queryParams("password").length() > 0) {
            try {
                entity.setPassword(request.queryParams("password"));
            } catch (CannotHashPasswordException e) {
                return new Pair<>(Status.FAILED_VALIDATION, null);
            }
        }

        // role
        StaffRole role = StaffRoleUtils.getStaffRole(request.queryParams("role-id"));
        entity.setRole(role);

        return new Pair<>(Status.OK, entity);
    }

    private Pair<Status, Object> createUpdateStaffAbsence(int entityId, Request request) {
        // validation
        DateTime from;
        DateTime to;
        try {
            // attempt to parse the dates
            from = new DateTime(request.queryParams("from").replace(" ", "T") + ":00");
            to = new DateTime(request.queryParams("to").replace(" ", "T") + ":00");
            if (from.isAfter(to)) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            return new Pair<>(Status.FAILED_VALIDATION, null);
        }

        // create and set ID
        StaffAbsence entity;
        if (createNew) {
            entity = new StaffAbsence();
        } else {
            entity = AbstractEntityUtils.getEntityById(StaffAbsence.class, entityId);
        }

        // dates
        entity.setFrom(from);
        entity.setTo(to);

        // staff
        Staff staff = StaffUtils.getStaff(request.queryParams("staff-id"));
        entity.setStaff(staff);

        return new Pair<>(Status.OK, entity);
    }

    private Pair<Status, Object> createUpdateStaffAvailability(int entityId, Request request) {
        // permission
        if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_STAFF_AVAILABILITIES)) {
            return new Pair<>(Status.NO_PERMISSION, null);
        }

        // validation
        String dayOfWeekString = request.queryParams("day-of-week");
        int dayOfWeek;
        String startTime = request.queryParams("start-time");
        String endTime = request.queryParams("end-time");
        int startTimeSecondsPastMidnight;
        int endTimeSecondsPastMidnight;
        try {
            dayOfWeek = Integer.parseInt(dayOfWeekString);
            startTimeSecondsPastMidnight = (Integer.parseInt(startTime.split(":")[0]) * 3600) + (Integer.parseInt(startTime.split(":")[1]) * 60);
            endTimeSecondsPastMidnight = (Integer.parseInt(endTime.split(":")[0]) * 3600) + (Integer.parseInt(endTime.split(":")[1]) * 60);
        } catch (NumberFormatException e) {
            return new Pair<>(Status.FAILED_VALIDATION, null);
        }

        // create and set ID
        StaffAvailability entity;
        if (createNew) {
            entity = new StaffAvailability();
        } else {
            entity = AbstractEntityUtils.getEntityById(StaffAvailability.class, entityId);
        }

        // dates
        try {
            entity.setDay(dayOfWeek);
            entity.setStartTime(new TimeOfDay(startTimeSecondsPastMidnight));
            entity.setEndTime(new TimeOfDay(endTimeSecondsPastMidnight));
        } catch (InvalidTimeOfDayException e) {
            return new Pair<>(Status.FAILED_VALIDATION, null);
        }

        // staff
        Staff staff = StaffUtils.getStaff(request.queryParams("staff-id"));
        entity.setStaff(staff);

        return new Pair<>(Status.OK, entity);
    }

    private Pair<Status, Object> createUpdateStaffRole(int entityId, Request request) {
        // permission
        if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_STAFF_ROLES)) {
            return new Pair<>(Status.NO_PERMISSION, null);
        }

        // validation
        if (request.queryParams("label").length() > 32
                || !request.queryParams("label").matches("[a-zA-Z\\-\\.' ]+")) {
            return new Pair<>(Status.FAILED_VALIDATION, null);
        }

        // create and set ID
        StaffRole entity;
        if (createNew) {
            entity = new StaffRole();
        } else {
            entity = AbstractEntityUtils.getEntityById(StaffRole.class, entityId);
        }

        // label
        entity.setLabel(request.queryParams("label"));

        // if it's new, we'll save it here so that the permissions can be added properly
        if (createNew) {
            AbstractEntityUtils.createEntity(StaffRole.class, entity);
        }

        // permissions
        entity.clearPermissions();
        Map<String, String[]> paramMap = request.queryMap().toMap();
        String key;
        Permission p;
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            // get key value
            key = entry.getKey();

            // is this a permission?
            if (!key.startsWith("permission-")) {
                continue;
            }

            // get permission
            key = key.substring(11);
            p = PermissionUtils.getPermission(key);
            if (p != null) entity.addPermission(p);
        }

        // don't let it be created again if it's new
        return createNew ? null : new Pair<>(Status.OK, entity);
    }

    private Pair<Status, Object> createUpdateTherapy(int entityId, Request request) {
        // permission
        if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_THERAPIES)) {
            return new Pair<>(Status.NO_PERMISSION, null);
        }

        // validation
        if (request.queryParams("name").length() > 64
                || request.queryParams("tracer-dose").length() > 32
                || !request.queryParams("name").matches("[a-zA-Z\\-\\.' ]+")
                || !request.queryParams("default-duration").matches("[0-9]+")
                || !request.queryParams("tracer-dose").matches("[a-zA-Z0-9\\-\\. ]+")) {
            return new Pair<>(Status.FAILED_VALIDATION, null);
        }

        // create and set ID
        Therapy entity;
        if (createNew) {
            entity = new Therapy();
        } else {
            entity = AbstractEntityUtils.getEntityById(Therapy.class, entityId);
        }

        // name
        entity.setName(request.queryParams("name"));

        // default-duration
        try {
            entity.setDuration(Integer.parseInt(request.queryParams("default-duration")));
        } catch (NumberFormatException e) {
            entity.setDuration(0);
        }

        // tracer required
        Tracer tracer = TracerUtils.getTracer(request.queryParams("tracer-required-id"));
        entity.setTracerRequired(tracer);

        // name
        entity.setTracerDose(request.queryParams("tracer-dose"));

        // camera type
        CameraType type = CameraTypeUtils.getCameraType(request.queryParams("camera-type-id"));
        entity.setCameraTypeRequired(type);

        return new Pair<>(Status.OK, entity);
    }

    private Pair<Status, Object> createUpdateTracer(int entityId, Request request) {
        // permission
        if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_TRACERS)) {
            return new Pair<>(Status.NO_PERMISSION, null);
        }

        // validation
        if (request.queryParams("name").length() > 64
                || !request.queryParams("name").matches("[a-zA-Z\\-\\.' ]+")
                || !request.queryParams("order-time").matches("[0-9]+")) {
            return new Pair<>(Status.FAILED_VALIDATION, null);
        }

        // create and set ID
        Tracer entity;
        if (createNew) {
            entity = new Tracer();
        } else {
            entity = AbstractEntityUtils.getEntityById(Tracer.class, entityId);
        }

        // name
        entity.setName(request.queryParams("name"));

        // order time
        try {
            entity.setOrderTime(Integer.parseInt(request.queryParams("order-time")));
        } catch (NumberFormatException e) {
            entity.setOrderTime(0);
        }

        return new Pair<>(Status.OK, entity);
    }

    private enum Status {
        OK,
        FAILED_VALIDATION,
        NO_PERMISSION
    }
}
