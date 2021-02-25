import {PolymerElement} from "@polymer/polymer";
import {html} from "@polymer/polymer/lib/utils/html-tag";

class ChangePasswordView extends PolymerElement {
    _attachDom(dom) {
        // Do not use a shadow root
        this.appendChild(dom);
    }

    static get template() {
        return html`
      <vaadin-vertical-layout style="width: 100%; height: 100%;">
        <div style="width:400px;display:flex;flex-direction:column;">
          <div style="padding:var(--lumo-space-l);flex-grow:1;">
            <vaadin-form-layout>
                <vaadin-password-field id="oldpassword" label="Stare hasło" placeholder="Wpisz stare hasło"></vaadin-password-field>
                <vaadin-password-field id="newpassword" label="Nowe hasło" placeholder="Wpisz nowe hasło"></vaadin-password-field>
                <vaadin-password-field id="repeatpassword" label="Powtórz hasło" placeholder="Powtórz nowe hasło"></vaadin-password-field>
                <vaadin-button theme="primary" id="savebtn">Zapisz</vaadin-button>
            </vaadin-form-layout>
          </div>
        </div>
      </vaadin-vertical-layout>
    `;
    }

    static get is() {
        return 'changepassword-view';
    }
}

customElements.define(ChangePasswordView.is, ChangePasswordView);
