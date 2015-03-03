package nuclibook.models;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.server.Renderable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@DatabaseTable(tableName = "therapies")
public class Therapy implements Renderable {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(width = 64)
	private String name;

	@DatabaseField(defaultValue = "60")
	private int duration;

	@DatabaseField(columnName = "tracer_required", foreign = true, foreignAutoRefresh = true)
	private Tracer tracerRequired;

	@DatabaseField(width = 32, columnName = "tracer_dose")
	private String tracerDose;

	@ForeignCollectionField(eager = true)
	private ForeignCollection<TherapyCameraType> therapyCameraTypes;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<PatientQuestion> patientQuestions;

    @DatabaseField(defaultValue = "true")
	private Boolean enabled;

	public Therapy() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Tracer getTracerRequired() {
		return tracerRequired;
	}

	public void setTracerRequired(Tracer tracerRequired) {
		this.tracerRequired = tracerRequired;
	}

	public String getTracerDose() {
		return tracerDose;
	}

	public void setTracerDose(String tracerDose) {
		this.tracerDose = tracerDose;
	}

	public List<CameraType> getCameraTypes() {
		ArrayList<CameraType> output = new ArrayList<>();
		CloseableIterator<TherapyCameraType> iterator = therapyCameraTypes.closeableIterator();
		try {
			CameraType ct;
			while (iterator.hasNext()) {
				ct = iterator.next().getCameraType();
				if (ct != null) output.add(ct);
			}
		} finally {
			iterator.closeQuietly();
		}
		return output;
	}

	public String getCameraTypesIdString() {
		List<CameraType> cameraTypeList = getCameraTypes();
		if (cameraTypeList.isEmpty()) return "0";
		StringBuilder sb = new StringBuilder();
		for (CameraType ct : cameraTypeList) {
			sb.append(ct.getId()).append(",");
		}
		return sb.substring(0, sb.length() - 1);
	}

	public void clearCameraTypes() {
		if (therapyCameraTypes == null) return;
		CloseableIterator<TherapyCameraType> iterator = therapyCameraTypes.closeableIterator();
		try {
			while (iterator.hasNext()) {
				AbstractEntityUtils.deleteEntity(TherapyCameraType.class, iterator.next());
			}
		} finally {
			iterator.closeQuietly();
		}
	}

	public void addCameraType(CameraType ct) {
		AbstractEntityUtils.createEntity(TherapyCameraType.class, new TherapyCameraType(this, ct));
	}

    public List<PatientQuestion> getPatientQuestion() {
        ArrayList<PatientQuestion> output = new ArrayList<>();
        CloseableIterator<PatientQuestion> iterator = patientQuestions.closeableIterator();
        try {
            PatientQuestion pq;
            while (iterator.hasNext()) {
                pq = iterator.next();
                if (pq != null) output.add(pq);
            }
        } finally {
            iterator.closeQuietly();
        }
        return output;
    }

    public String getPatientQuestionIdString() {
        List<PatientQuestion> patientQuestionList = getPatientQuestion();
        if (patientQuestionList.isEmpty()) return "0";
        StringBuilder sb = new StringBuilder();
        for (PatientQuestion pq : patientQuestionList) {
            sb.append(pq.getId()).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public void clearPatientQuestion() {
        if (patientQuestions == null) return;
        CloseableIterator<PatientQuestion> iterator = patientQuestions.closeableIterator();
        try {
            while (iterator.hasNext()) {
                AbstractEntityUtils.deleteEntity(PatientQuestion.class, iterator.next());
            }
        } finally {
            iterator.closeQuietly();
        }
    }

    public void addPatientQuestion(PatientQuestion pq) {
        AbstractEntityUtils.createEntity(PatientQuestion.class, pq);
        pq.setTherapy(this);
    }

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public HashMap<String, String> getHashMap() {
		return new HashMap<String, String>(){{
			put("id", getId().toString());
			put("name", getName());
			put("camera-type-ids", "IDLIST:" + getCameraTypesIdString());
			put("default-duration", ((Integer) getDuration()).toString());
			put("tracer-required-id", getTracerRequired().getId().toString());
			put("tracer-required-name", getTracerRequired().getName());
			put("tracer-dose", getTracerDose());
		}};
	}
}
