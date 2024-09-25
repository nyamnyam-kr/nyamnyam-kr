class Calendar {
    constructor() {
        // 요소 초기화
        this.body = document.body;
        this.calendarElement = document.getElementById("calendar");
        this.formEvent = document.getElementById("forms-event");
        this.btnNewEvent = document.getElementById("btn-new-event");
        this.btnSaveEvent = document.getElementById("btn-save-event");
        this.modalTitle = document.getElementById("modal-title");
        this.calendarObj = null;
        this.selectedEvent = null;
        this.newEventData = null;
    }

    // 이벤트 클릭 시 처리
    onEventClick(e) {
        if (this.formEvent) {
            this.formEvent.reset();
            this.formEvent.classList.remove("was-validated");
        }
        this.newEventData = null;
        this.modalTitle.textContent = "Edit Event";
        this.modal.show();
        this.selectedEvent = e.event;
        document.getElementById("event-title").value = this.selectedEvent.title;
        document.getElementById("event-category").value = this.selectedEvent.classNames[0];
    }

    // 선택 시 처리
    onSelect(e) {
        if (this.formEvent) {
            this.formEvent.reset();
            this.formEvent.classList.remove("was-validated");
        }
        this.selectedEvent = null;
        this.newEventData = e;
        this.modalTitle.textContent = "Add New Event";
        this.modal.show();
        if (this.calendarObj) {
            this.calendarObj.unselect();
        }
    }

    // 캘린더 초기화
    init() {
        const now = new Date();
        const events = [
            { title: "Interview - Backend Engineer", start: now, end: now, className: "bg-primary" },
            { title: "Meeting with CT Team", start: new Date(now.getTime() + 13e6), end: now, className: "bg-warning" },
            { title: "Meeting with Mr. Shield", start: new Date(now.getTime() + 308e6), end: new Date(now.getTime() + 338e6), className: "bg-info" },
            { title: "Interview - Frontend Engineer", start: new Date(now.getTime() + 6057e4), end: new Date(now.getTime() + 153e6), className: "bg-secondary" },
            { title: "Phone Screen - Frontend Engineer", start: new Date(now.getTime() + 168e6), className: "bg-success" },
            { title: "Buy Design Assets", start: new Date(now.getTime() + 33e7), end: new Date(now.getTime() + 3308e5), className: "bg-primary" },
            { title: "Setup Github Repository", start: new Date(now.getTime() + 1008e6), end: new Date(now.getTime() + 1108e6), className: "bg-danger" },
            { title: "Meeting with Mr. Shreyu", start: new Date(now.getTime() + 2508e6), end: new Date(now.getTime() + 2508e6), className: "bg-dark" }
        ];

        this.calendarObj = new FullCalendar.Calendar(this.calendarElement, {
            plugins: [],
            slotDuration: "00:30:00",
            slotMinTime: "07:00:00",
            slotMaxTime: "19:00:00",
            themeSystem: "default",
            buttonText: {
                today: "Today",
                month: "Month",
                week: "Week",
                day: "Day",
                list: "List",
                prev: "Prev",
                next: "Next"
            },
            initialView: "dayGridMonth",
            handleWindowResize: true,
            height: window.innerHeight - 300,
            headerToolbar: {
                left: "prev,next today",
                center: "title",
                right: "dayGridMonth,timeGridWeek,timeGridDay,listMonth"
            },
            initialEvents: events,
            editable: true,
            droppable: true,
            selectable: true,
            dateClick: (e) => this.onSelect(e),
            eventClick: (e) => this.onEventClick(e)
        });

        this.calendarObj.render();
    }
}

// DOMContentLoaded 이벤트에서 Calendar 초기화
document.addEventListener("DOMContentLoaded", () => {
    const calendar = new Calendar();
    calendar.init();
});
